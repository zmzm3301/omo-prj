package com.omo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omo.dto.JwtToken;
import com.omo.dto.MailCode;
import com.omo.dto.Member;
import com.omo.dto.Result;
import com.omo.dto.Search;
import com.omo.repository.CodeRepository;
import com.omo.repository.MemberRepository;
import com.omo.repository.PostRepository;
import com.omo.utils.JwtTokenProvider;
import com.omo.utils.UtilService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
 
	@Autowired
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final EntityManagerFactory entityManagerFactory;
    private final RegisterMail registerMail;
    private final CodeRepository codeRepository;
    private final UtilService utilService;
    

    private static final int refreshTokenExpiresIn = (60 * 60 * 24 * 7) + (60 * 60 * 9);
 
    
    public boolean matchesBcrypt(String planeText, String hashValue) {
    	  return passwordEncoder.matches(planeText, hashValue);
    	}
    
    
    @Transactional
    public JwtToken signin(String username, String password, HttpServletResponse response) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
    	Member member = memberRepository.findMemberByUsername(username);
    	if(member == null) {
    		String notfound = "존재하지 않는 계정입니다.";
    		return JwtToken.builder().errorMessage(notfound).build();
    	}

    	
    	if(member.getPasswordwrong() == 10) {
    		member.setActivated(false);
    	}
    	
    	if(member.isActivated() == false) {
    		String isActive = "비활성화 된 계정입니다. 비밀번호를 변경해주세요.";
    		return JwtToken.builder().errorMessage(isActive).build();
    	}

    	if(matchesBcrypt(password, member.getPassword())) {
    		password = member.getPassword();
    	}else {
    		member.setPasswordwrong(member.getPasswordwrong()+1);
    		String wrong = "비밀번호가" + member.getPasswordwrong() + "회 틀렸습니다. 10회 오류시 계정은 비활성화 됩니다.";
    		return JwtToken.builder().errorMessage(wrong).build();
    		
    	}

    	
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken tokenInfo = jwtTokenProvider.generateToken(authentication);
        
        // Refresh Token을 쿠키에 담기
        Cookie refreshTokenCookie = new Cookie("refreshToken", tokenInfo.getRefreshToken());
        refreshTokenCookie.setMaxAge(refreshTokenExpiresIn); // 7일간 유효
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS 프로토콜 사용 시 설정
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        
        
        member.setUpdatedAt(formattedDateTime);
        member.setPasswordwrong(0);
        
        return tokenInfo;
    }
    
    @Transactional
    public String signup(Member member) {

    	LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        Member user = Member.builder()
                .username(member.getUsername())
                .password(passwordEncoder.encode(member.getPassword()))
                .name(member.getName())
                .nickname(member.getNickname())
                .birth(member.getBirth())
                .sex(member.getSex())
                .createdAt(formattedDateTime)
                .passwordwrong(0)
                .activated(true)
                .build();

		user.getRoles().add("USER");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(user);
		transaction.commit();
		entityManager.close();
		
		memberRepository.save(user);
		
        return "Success!";
    }
    
    @Transactional
    public Search checkId(Search search)throws Exception {
    	String username = search.getUsername();
    	Member user = memberRepository.findByUsername(username).orElse(null);
    	Search check = new Search();
    	System.out.println(username);
    	System.out.println(user);
    	
    	try {
    	if(user != null) {
    		check.setCheck(false);
    		
    	}else {
    		check.setCheck(true);
    		MailCode del = codeRepository.findByEmail(username);
    		if(del != null) {
    			codeRepository.delete(del);
    		}
    	   String code = registerMail.sendSimpleMessage(username);
    	   System.out.println("인증코드 : " + code);
    	   MailCode mailcode = new MailCode();
    	   mailcode.setCode(code);
    	   mailcode.setEmail(username);
    	   codeRepository.save(mailcode);
    		
    	}
    	return check;
    }catch(MailException e){
    	return null;
    }
    }
    
    @Transactional
    public String check_nickname(Search search) {
    	String nickname = search.getNickname();
    	System.out.println(nickname);
    	Member member = memberRepository.findByNickname(nickname);
    	System.out.println(member);
    	if(member == null) {
    		return "사용가능한 닉네임입니다.";
    	}
    	return "이미 존재하는 닉네임입니다.";
    }
    
    @Transactional
    public Member searchId(Search search) {
        String name = search.getName();
        String birth = search.getBirth();
        LocalDate birthDate = LocalDate.parse(birth);
        
        Member user = memberRepository.findByNameAndBirth(name, birthDate);
        Member data = new Member();
        
        data.setName(user.getName());
        data.setUsername(user.getUsername());
        

            return data;
    
    }
    
    @Transactional
    public Search searchPw(Search search) throws Exception {
    	String username = search.getUsername();
    	String name = search.getName();
    	String birth = search.getBirth();
    	LocalDate birthDate = LocalDate.parse(birth);
    	System.out.println(username);
    	System.out.println(name);
    	System.out.println(birthDate);
    	
    	Member user = memberRepository.findByUsernameAndNameAndBirth(username, name, birthDate); 
    	Search check = new Search();
    	try {
    	if(user != null) {
    		check.setCheck(true);
    		MailCode del = codeRepository.findByEmail(username);
    		if(del != null) {
    			codeRepository.delete(del);
    		}
    	   String code = registerMail.sendSimpleMessage(username);
    	   System.out.println("인증코드 : " + code);
    	   MailCode mailcode = new MailCode();
    	   mailcode.setCode(code);
    	   mailcode.setEmail(username);
    	   codeRepository.save(mailcode);
    		
    	}else {
    		check.setCheck(false);
    		
    	}
    	return check;
    }catch(MailException e){
    	return null;
    }
    }
    
    @Transactional
    public Search changePw(Search search) {
    	String username = search.getUsername();
    	String newPassword = search.getNewPassword();
    	String confirmNewPassword = search.getConfirmNewPassword();	
        Member user = memberRepository.findByUsername(username).orElse(null);
        Search result = new Search();
        

        if (!newPassword.equals(confirmNewPassword)) {
            result.setErrorMessage("비밀번호가 일치하지 않습니다.");
            result.setCheck(false);
        	return result;
        }

        if(matchesBcrypt(newPassword, user.getPassword())) {
        	result.setCheck(false);
        	result.setErrorMessage("이전 비밀번호와 동일한 비밀번호입니다.");
        	return result;
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setActivated(true);
        user.setPasswordwrong(0);
        memberRepository.save(user);
        result.setCheck(true);
        
        return result;
    }
    
    
	@Transactional
	public JwtToken refresh(String token, HttpServletRequest request, HttpServletResponse response) {
		JwtToken reToken = jwtTokenProvider.regenerateToken(token, request, response);
		
    	Cookie refreshTokenCookie = new Cookie("refreshToken", reToken.getRefreshToken());
        refreshTokenCookie.setMaxAge(refreshTokenExpiresIn); // 7일간 유효
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true); // HTTPS 프로토콜 사용 시 설정
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
        
		return reToken;
	}
	
	@Transactional
	public Member checkPw(Member password, Authentication authentication, HttpServletRequest request) {
		String token = utilService.getAccessToken(request);
		
	    Member member = memberRepository.findByUsername(authentication.getName())
	      .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + authentication.getName()));
	    if(jwtTokenProvider.validateToken(token)) {

	        Member info = new Member();
	        if(matchesBcrypt(password.getPassword(), member.getPassword())) {
	        	info.setUsername(member.getUsername());
	        	info.setBirth(member.getBirth());
	        	info.setName(member.getName());
	        	info.setNickname(member.getNickname());
	        	info.setSex(member.getSex());
	            return info;
	        }
	        return null;
	    }
	    return null;
	}

	@Transactional
	public String editProfile(Member member, Authentication authentication, HttpServletRequest request) {
		String token = utilService.getAccessToken(request);
		
	    Member members = memberRepository.findByUsername(authentication.getName()).orElse(null);	    
	    if(jwtTokenProvider.validateToken(token)) {
	    	if(member.getPassword() == null) {
		        members.setNickname(member.getNickname());
		        members.setSex(member.getSex());
		        members.setBirth(member.getBirth());
		        memberRepository.save(members);
		        return "true";
	    	}else {
	        members.setNickname(member.getNickname());
	        members.setSex(member.getSex());
	        members.setBirth(member.getBirth());
	        members.setPassword(passwordEncoder.encode(member.getPassword()));
	        memberRepository.save(members);
	        return "true";
	    	}
	}return "false";
}
	
	@Transactional
	public List<Member> memberList(Authentication authentication, HttpServletRequest request){
		String token = utilService.getAccessToken(request);
		
	    Member member = memberRepository.findByUsername(authentication.getName()).orElse(null);
	    
	    if(jwtTokenProvider.validateToken(token)) {
			List<Member> members = memberRepository.findAll();
			return members;
	    }else {
	    	return null;
	    }
	    
	}
	
	@Transactional
	public Result deleteMember(List<Member> member, Authentication authentication, HttpServletRequest request) {
	    String token = utilService.getAccessToken(request);
	    Result result = new Result();
	    boolean isSuccess = true; // 모든 멤버 삭제 성공 여부

	    for (Member m : member) {
	        Member target = memberRepository.findByNo(m.getNo()).orElse(null);
	        if ( jwtTokenProvider.validateToken(token)
	        		&& authentication.getAuthorities().stream()
	                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
	        	target.getRoles().clear();
	            memberRepository.delete(target);
	        } else {
	            isSuccess = false;
	        }
	    }
	    result.setEmpty(isSuccess);
	    return result;
	}

	@Transactional
	public Result changeRole(List<Member> member, Authentication authentication, HttpServletRequest request) {
		String token = utilService.getAccessToken(request);
		Result result = new Result();
		for(Member m : member) {
			if(jwtTokenProvider.validateToken(token) && authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
			Member target = memberRepository.findByNo(m.getNo()).orElse(null);
			if(target.getRoles().stream().anyMatch(authority -> authority.equals("ADMIN"))) {
				target.getRoles().clear();
				target.getRoles().add(m.getRoles().toString());
			}else {
			target.getRoles().clear();
			target.getRoles().add("ADMIN");
			}
			memberRepository.save(target);
			result.setEmpty(true);
		}
		}
		return result;
	}
	
	}
