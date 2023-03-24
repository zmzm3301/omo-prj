package com.omo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.omo.dto.Comment;
import com.omo.dto.Post;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	List<Comment> findAllById(Long id);

	List<Comment> findByPost(Post no);



}
