package com.omo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omo.dto.Comment;
import com.omo.dto.Post;
import com.omo.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	@Autowired
	private CommentService service;
	
//	코멘트 불러오기
	@GetMapping(path="/list/{no}")
	public List<Comment> getComments(@PathVariable Post no) {
		return service.getComments(no);
	}
//	코맨트 추가
	@PostMapping(path="/add/{no}")
	public ResponseEntity<Comment> add_comment(@RequestBody Comment comment, @PathVariable Post no, Authentication authentication, HttpServletRequest request){
		return new ResponseEntity<Comment>(service.add_comment(comment, no, authentication, request), HttpStatus.OK);
	}
//	코맨트 삭제
	@DeleteMapping(path="/delete/{no}")
	public ResponseEntity<Comment> delete(@PathVariable Comment no,Authentication authentication, HttpServletRequest request) {
		return new ResponseEntity<Comment>(service.delete(no, authentication, request), HttpStatus.OK);
	}
	
//	코맨트 수정
	@PostMapping(path="/update/{no}")
	public ResponseEntity<Comment> update(@PathVariable Comment no,@RequestBody Comment comment ,Authentication authentication, HttpServletRequest request) {
		return new ResponseEntity<Comment>(service.update(no, comment,authentication, request), HttpStatus.OK);
	}

	
}
