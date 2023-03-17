package com.omo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omo.dto.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	Optional<Comment> findByCid(int Cid);
}
