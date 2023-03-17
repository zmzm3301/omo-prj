package com.omo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omo.dto.Comment;
import com.omo.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService service;
	
	@GetMapping(path="/list")
	public List<Comment> getComments() {
		return service.showComments();
	}
	
	@DeleteMapping(path="/delete/{cid}")
	public void delete(@PathVariable int cid) {
		System.out.println("cid: " + cid);
		service.delete(cid);
	}
	
	@PostMapping(path="/save")
	public Comment insertSave(Comment comment) {
		System.out.println("???");
		System.out.println("commentController: " + comment.getContent());
		service.join(comment);
		return comment;
	}
	
}
