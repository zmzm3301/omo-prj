package com.omo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omo.dao.CommentRepository;
import com.omo.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository dao;

	@Override
	public int join(Comment comment) {
		System.out.println("comment: " + comment);
		Comment joinComment = dao.save(comment);
		System.out.println("joinComment: " + joinComment);
        return joinComment.getCid();
	}

	@Override
	public Optional<Comment> find(int cid) {
		return dao.findById(cid);
	}

	@Override
	public void delete(int cid) {
		dao.deleteById(cid);
	}

	@Override
	public List<Comment> showComments() {
		List<Comment> comments = dao.findAll();
		return comments;
	}

}
