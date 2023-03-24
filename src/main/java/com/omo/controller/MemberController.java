package com.omo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.omo.dto.JwtToken;
import com.omo.dto.Member;
import com.omo.dto.MemberLoginRequestDto;
import com.omo.dto.Search;
import com.omo.repository.MemberRepository;
import com.omo.service.MemberService;
import com.omo.utils.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletRequest request;
    private MemberRepository memberRepository;
    
    @Autowired
    public MemberController(MemberService memberService, HttpServletRequest request, JwtTokenProvider jwtTokenProvider, MemberRepository memberRepository) {
        this.memberService = memberService;
        this.request = request;
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
    }
 
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) {
        String username = memberLoginRequestDto.getUsername();
        String password = memberLoginRequestDto.getPassword();
        
        JwtToken tokenInfo = memberService.signin(username, password, response);

        if(tokenInfo.getErrorMessage() == null) {
        	HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", tokenInfo.getAccessToken());
            String responseBody = "Success!";
            ResponseEntity<String> res = new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
        	return res;
        }
        String message = tokenInfo.getErrorMessage();
        
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }

    
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Member member) {
    	return new ResponseEntity<String>(memberService.signup(member), HttpStatus.OK);
    }
    
    @PostMapping("/checkid")
    public ResponseEntity<Search> checkId(@RequestBody Search search) throws Exception{
    	return new ResponseEntity<Search>(memberService.checkId(search), HttpStatus.OK);
    }
    
    @PostMapping("/search_id")
    public ResponseEntity<Member> SearchId(@RequestBody Search search){
    	return new ResponseEntity<Member>(memberService.searchId(search), HttpStatus.OK);
    }
    
    @PostMapping("/search_pw")
    public ResponseEntity<Search> SearchPw(@RequestBody Search search) throws Exception{
    	return new ResponseEntity<Search>(memberService.searchPw(search), HttpStatus.OK);
    }
    @PostMapping("/change_pw")
    public ResponseEntity<Search> ChangePw(@RequestBody Search search){
    	return new ResponseEntity<Search>(memberService.changePw(search), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {

        return ResponseEntity.ok("Successfully logged out.");
    }
    
    @PostMapping("/check_nickname")
    public ResponseEntity<String> check_nickname(@RequestBody Search search){
    	return new ResponseEntity<String>(memberService.check_nickname(search), HttpStatus.OK);
    }

    
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {
        String tokenWithPrefix = request.getHeader("Authorization");
        String token = "";
        if (tokenWithPrefix != null && tokenWithPrefix.startsWith("Bearer ")) {
            token = tokenWithPrefix.substring(7);
            // 이제 token 변수에는 접두어 "Bearer "를 제거한 토큰 값이 저장되어 있음
        }
        
        JwtToken regeneratedToken = memberService.refresh(token, request, response);
        String toke = regeneratedToken.getAccessToken();
        
        Authentication username = jwtTokenProvider.getAuthentication(toke);
        Member member = memberRepository.findByUsername(username.getName()).orElse(null);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", toke);
        String responseBody = member.getNickname();
        ResponseEntity<String> res = new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
        
        System.out.println(res);
        return res;
    }
    
    @PostMapping("/checkpw")
    public ResponseEntity<Member> checkPw(@RequestBody Member password, Authentication authentication, HttpServletRequest request) {
    	return new ResponseEntity<Member>(memberService.checkPw(password, authentication, request), HttpStatus.OK);
    }

    @PostMapping("/editprofile")
    public ResponseEntity<String> editProfile(@RequestBody Member member, Authentication authentication, HttpServletRequest request){
    	return new ResponseEntity<String>(memberService.editProfile(member,authentication, request), HttpStatus.OK);
    }
    
}