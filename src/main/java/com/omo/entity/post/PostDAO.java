package com.omo.entity.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.omo.dto.post.PostRequest;

import jakarta.transaction.Transactional;

public interface PostDAO extends JpaRepository<Post, Long> {
    String UPDATE_POST = "UPDATE tb_post " +
    		"SET TITLE = :#{#postRequest.title}, " +
    		"CONTENT = :#{#postRequest.content}, " +
    		"UPDATE_TIME = NOW() " +
    		"WHERE ID = :#{#postRequest.id}";
    
    @Transactional
    @Modifying(clearAutomatically = true) 
    @Query(value = UPDATE_POST, nativeQuery = true)
    public int updatePost(@Param("postRequest") PostRequest postRequest);
   
}
