package com.omo.service;

import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omo.dto.post.PostRequest;
import com.omo.dto.post.PostResponse;
//import com.omo.entity.post.PostDAO;
import com.omo.entity.post.Post;
import com.omo.entity.post.PostDAO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
	
	private final PostDAO dao;
	
	@Transactional
	@Override
	public Post save(PostRequest postSave) {
		return dao.save(postSave.toEntity());
	}
	
	@Transactional(readOnly = true)
	@Override
	public HashMap<String, Object> findAll(Integer page, Integer size) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println("page:: " + page);
		Page<Post> list = dao.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
		System.out.println("list:: " + list);
		resultMap.put("list", list.stream().map(PostResponse::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
	}

	@Override
	public PostResponse findById(Long id) {
		return new PostResponse(dao.findById(id).get());
	}
	
	@Transactional
	@Override
	public void updatePost (PostRequest postRequest, Long id) {
		Post post = dao.findById(id).orElseThrow(()->new IllegalArgumentException("해당게시물 없습니다"+id));
		post.update(postRequest.getTitle(), postRequest.getContent());
	}
	
//	public int updatePostReadCntInc(Long id) {
//		return dao.updatePostReadCntInc(id);
//	}
//	
	@Override
	public void deleteById(Long id) {
		dao.deleteById(id);
	}
}
