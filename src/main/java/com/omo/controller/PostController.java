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

import com.omo.dto.Post;
import com.omo.service.PostService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/post")
public class PostController {
	@Autowired
	private PostService postService;

    @PostMapping("/add_post")
    public ResponseEntity<String> add(@RequestBody Post post, Authentication authentication, HttpServletRequest request){
    	return new ResponseEntity<String>(postService.add(post, authentication, request), HttpStatus.OK);
    }
    
    
    @GetMapping("/list")
    public List<Post> list() {
    	return postService.list();
    }
	
    @GetMapping(path="/detail/{no}")
    public Post detail(@PathVariable Post no) {
    	return postService.detail(no);
    }
    
    @DeleteMapping(path="/delete/{no}")
	public ResponseEntity<Post> delete(@PathVariable Post no,Authentication authentication, HttpServletRequest request) {
		return new ResponseEntity<Post>(postService.delete(no, authentication, request), HttpStatus.OK);
	}
    
    @PostMapping(path="/update/{no}")
    public ResponseEntity<Post> update(@PathVariable Post no, @RequestBody Post post, Authentication authentication, HttpServletRequest request){
    	return new ResponseEntity<Post>(postService.update(no, post, authentication, request), HttpStatus.OK);
    }
    
    @PostMapping(path="/myboard")
    public List<Post> myboard(Authentication authentication,HttpServletRequest request){
    	return postService.myboard(authentication,request);
    }
    
}
