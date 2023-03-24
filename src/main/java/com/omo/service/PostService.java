package com.omo.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.omo.dto.Post;

import jakarta.servlet.http.HttpServletRequest;

public interface PostService {
	String add(Post post, Authentication authentication, HttpServletRequest request);
	List<Post> list();
	Post detail(Post no);
	Post delete(Post no, Authentication authentication, HttpServletRequest request);
	Post update(Post no, Post post, Authentication authentication, HttpServletRequest request);
	List<Post> myboard(Authentication authentication,HttpServletRequest request);
}
