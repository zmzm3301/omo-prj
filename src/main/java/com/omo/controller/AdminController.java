package com.omo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omo.dto.Member;
import com.omo.dto.Result;
import com.omo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/member")
	public List<Member> memberList(Authentication authentication, HttpServletRequest request){
		return memberService.memberList(authentication, request);
	}
	
	@PostMapping(path="/delete")
	public ResponseEntity<Result> deleteMember(@RequestBody List<Member> members, Authentication authentication, HttpServletRequest request){
		return new ResponseEntity<Result>(memberService.deleteMember(members, authentication, request), HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<Result> changeRole(@RequestBody List<Member> members, Authentication authentication, HttpServletRequest request){
		return new ResponseEntity<Result>(memberService.changeRole(members,authentication, request), HttpStatus.OK);
	}

}
