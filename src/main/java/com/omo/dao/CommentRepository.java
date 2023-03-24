package com.omo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omo.dto.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	Optional<Comment> findByCid(int Cid);
}
