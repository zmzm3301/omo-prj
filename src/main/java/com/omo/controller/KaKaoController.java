package com.omo.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omo.dto.KPerson;
import com.omo.service.KaKaoService;

import ch.qos.logback.core.subst.Parser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.minidev.json.JSONArray;


@RestController
public class KaKaoController {
	
	@Autowired
	private KaKaoService kservice;
	
	@GetMapping(path="/klogin")
    public HashMap<String, Object> login(@RequestParam("code") String code, HttpSession session, 
    		HttpServletResponse response, KPerson email) {
		String ar = kservice.getKakaoAccessToken(code);
		String[] ars = ar.split(" ");
		if (ars.length > 0) {
			String access_Token = ars[0];
			System.out.println("access_Token" + access_Token);
			HashMap<String, Object> userInfo = kservice.getUserInfo(access_Token);
			KPerson kperson = kservice.kperson((String) userInfo.get("email"));
		    
		    if (userInfo.get("email") != null) {
		        session.setAttribute("userId", userInfo.get("email"));
		        session.setAttribute("access_Token", access_Token);
		    }
		    
		    Cookie cnickName = new Cookie("nickName", (String) userInfo.get("nickName"));
		    Cookie cemail = new Cookie("email", (String) userInfo.get("email"));

		    Cookie authority = new Cookie("authority", kperson.getAuthority());
		    
		    cnickName.setHttpOnly(true);
		    cemail.setHttpOnly(true);
		    authority.setHttpOnly(true);
		    
		    cnickName.setSecure(true);
		    cemail.setSecure(true);
		    authority.setSecure(true);

			response.addCookie(cnickName);
			response.addCookie(cemail);
			response.addCookie(authority);
			
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
	public String getCookie(@CookieValue String nickName, 
			@CookieValue(required = false) String email, @CookieValue String authority) {
		String arr = nickName + " " + email + " " + authority;

		return arr;
	}
	
	@PostMapping("/addkperson")
	public KPerson addkperson(KPerson kperson) {
		if(kperson.getEmail() == null) {
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
	
	@PutMapping(path="/upKperson/{id}")
	public void upKerson(@PathVariable int id, String authority, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("??" + id + authority);
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        if (cookie.getName().equals("authority")) {
		        	System.out.println("cookie: " + authority);
		            cookie.setValue(authority);
		            response.addCookie(cookie);
		            break;
		        }
		    }	
		}
		
		kservice.updateKPerson(id, authority);
	}
	
	@PutMapping(path="/all/update/{id}")
	public void upAllKperson(@PathVariable(required = false, name = "id") String id, @RequestBody String[] authority) {
		System.out.println("Tid: " + id);
		
		String[] idArray = id.split(",");
		int[] intIds = new int[idArray.length];
	    for (int i = 0; i < idArray.length; i++) {
		    intIds[i] = Integer.parseInt(idArray[i]);
		    kservice.updateKPerson(intIds[i], authority[i]);
	    }
		
		System.out.println("authority: " + authority[0]);
		
	}

}