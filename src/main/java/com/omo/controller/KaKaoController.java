package com.omo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omo.dto.KPerson;
import com.omo.service.KaKaoService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@RestController
public class KaKaoController {
	
	@Autowired
	private KaKaoService kservice;
	
	@GetMapping(path="/klogin")
    public HashMap<String, Object> login(@RequestParam("code") String code, HttpSession session, 
    		HttpServletResponse response) {
		String ar = kservice.getKakaoAccessToken(code);
		String[] ars = ar.split(" ");
		if (ars.length > 0) {
			String access_Token = ars[0];
			
			HashMap<String, Object> userInfo = kservice.getUserInfo(access_Token);
		    
		    if (userInfo.get("email") != null) {
		        session.setAttribute("userId", userInfo.get("email"));
		        session.setAttribute("access_Token", access_Token);
		    }
		    
		    Cookie cnickName = new Cookie("nickName", (String) userInfo.get("nickName"));
		    Cookie cemail = new Cookie("email", (String) userInfo.get("email"));
		    
		    cnickName.setHttpOnly(true);
		    cemail.setHttpOnly(true);
		    
		    cnickName.setSecure(true);
		    cemail.setSecure(true);

			response.addCookie(cnickName);
			response.addCookie(cemail);
			
	        return userInfo;
		} else {
			return null;
		}
		   
    }
	
	@GetMapping("/deleteCookie")
	public void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies(); // 모든 쿠키의 정보를 cookies에 저장
	    if(cookies != null){ // 쿠키가 한개라도 있으면 실행
		    for(int i=0; i< cookies.length; i++){
			    cookies[i].setMaxAge(0); // 유효시간을 0으로 설정
			    response.addCookie(cookies[i]); // 응답 헤더에 추가
		    }
	    }
	}
	
	@GetMapping("/getCookie")
	public String getCookie(@CookieValue String nickName, @CookieValue(required = false) String email) {
		String arr = nickName + " " + email;

		return arr;
	}
	
	@PostMapping("/addkperson")
	public KPerson addkperson(KPerson kperson) {
		if(kperson.getEmail() != null) {
			kservice.addkperson(kperson);
		}
		
		return kperson;
	}
	
	@GetMapping(path="/kpersons")
	public List<KPerson> Kpersons(){
		return kservice.Kpersons();
	}
	
	@DeleteMapping(path="/delkperson/{email}")
	public void delKperson(@PathVariable String email) {
		kservice.delKperosn(email);
	}
	
	@PutMapping(path="/upKperson/{email}")
	public int upKerson(@PathVariable String email, String authority) {
		return kservice.updateKPerson(email, authority);
	}

}