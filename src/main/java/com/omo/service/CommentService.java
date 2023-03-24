package com.omo.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.omo.dto.Comment;
import com.omo.dto.Post;

import jakarta.servlet.http.HttpServletRequest;

public interface CommentService {
	List<Comment> getComments(Post no);

	Comment add_comment(Comment comment, Post no, Authentication authentication, HttpServletRequest request);

	Comment delete(Comment no, Authentication authentication, HttpServletRequest request);

	Comment update(Comment no, Comment comment, Authentication authentication, HttpServletRequest request);
}
