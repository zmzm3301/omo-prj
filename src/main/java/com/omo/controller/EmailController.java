package com.omo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.omo.dto.MailCode;
import com.omo.dto.Search;
import com.omo.repository.CodeRepository;
import com.omo.service.RegisterMail;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class EmailController {
	
	
	@Autowired
	private RegisterMail registerMail;
	@Autowired
	private CodeRepository codeRepository;
	
	@PostMapping("/login/mailConfirm")
	@ResponseBody
	public String mailConfirm(@RequestParam("email") String email) throws Exception {
		MailCode del = codeRepository.findByEmail(email);
		if(del != null) {
		codeRepository.delete(del);
	}
	   String code = registerMail.sendSimpleMessage(email);
	   System.out.println("인증코드 : " + code);
	   MailCode mailcode = new MailCode();
	   mailcode.setCode(code);
	   mailcode.setEmail(email);
	   codeRepository.save(mailcode);
	   return code;
	}
	
	@PostMapping("/login/check")
	public ResponseEntity<Search> CheckCode(@RequestBody MailCode mailcode) {
		return new ResponseEntity<Search>(registerMail.checkCode(mailcode), HttpStatus.OK);
	}
	
	
}
