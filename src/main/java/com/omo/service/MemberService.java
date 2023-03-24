package com.omo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omo.dto.JwtToken;
import com.omo.dto.Member;
import com.omo.dto.Post;
import com.omo.dto.Search;
import com.omo.dao.MemberRepository;
import com.omo.dao.PostRepository;
import com.omo.utils.JwtTokenProvider;

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
        
        
        member.setUpdatedAt(LocalDateTime.now());
        member.setPasswordwrong(0);
        
        return tokenInfo;
    }
    
    public Member signup(Member member) {


        Member user = Member.builder()
                .username(member.getUsername())
                .password(passwordEncoder.encode(member.getPassword()))
                .name(member.getName())
                .birth(member.getBirth())
                .sex(member.getSex())
                .createdAt(LocalDateTime.now())
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

		
        return memberRepository.save(user);
    }
    
    public String searchId(Search search) {
        String name = search.getName();
        String birth = search.getBirth();
        LocalDate birthDate = LocalDate.parse(birth);
        
        Member user = memberRepository.findByNameAndBirth(name, birthDate);
        
        if (user == null) {
            // 입력한 이름과 생년월일에 해당하는 회원이 없는 경우 처리
            return "not found";
        } else {
            return user.getUsername();
        }
    }

    public String searchPw(Search search) {
    	String username = search.getUsername();
    	String name = search.getName();
    	String birth = search.getBirth();
    	LocalDate birthDate = LocalDate.parse(birth);
    	
    	Member user = memberRepository.findByUsernameAndNameAndBirth(username, name, birthDate);
    	
    	if(user == null) {
    		return "not found";
    	}else {
    		return user.getUsername();
    	}
    	
    }
    
    @Transactional
    public String changePw(Search search) {
    	String username = search.getUsername();
    	String newPassword = search.getNewPassword();
    	String confirmNewPassword = search.getConfirmNewPassword();	
        Member user = memberRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return "false";
        }

        if (!newPassword.equals(confirmNewPassword)) {
            return "Passwords do not match";
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        user.setActivated(true);
        user.setPasswordwrong(0);
        memberRepository.save(user);

        return "Success!";
    }
    
    public Post add_post(Post post, Authentication authentication) {
        
        Member author = memberRepository.findByUsername(authentication.getName()).orElse(null);


    	Post posts = Post.builder()
    			.title(post.getTitle())
    			.content(post.getContent())
    			.author(author)
    			.build();
    	
    	return postRepository.save(posts);
    }

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
    
    
}