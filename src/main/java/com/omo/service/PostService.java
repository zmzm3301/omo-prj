package com.omo.service;

import java.util.HashMap;
import java.util.List;

import com.omo.dto.post.PostRequest;
import com.omo.dto.post.PostResponse;
import com.omo.entity.post.Post;

public interface PostService {
	Post save(PostRequest postSave);
	HashMap<String, Object> findAll(Integer page, Integer size);
	PostResponse findById(Long id);
	void updatePost (PostRequest postRequest, Long id);
	void deleteById(Long id);
}
