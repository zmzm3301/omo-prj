package com.omo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.omo.dto.Member;
import com.omo.dto.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query("SELECT p FROM Post p ORDER BY CASE WHEN p.notice = true THEN 0 ELSE 1 END, p.id DESC")
	List<Post> findAllOrderByNoticeAndIdDesc();

	void findByAuthor(Long no);

	List<Post> findAllByAuthor(Member author);

	
	
}
