package com.omo.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.omo.dto.post.PostRequest;
import com.omo.dto.post.PostResponse;
import com.omo.service.PostService;


@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired
    private PostService service;

    @GetMapping(path="/list")
    public HashMap<String, Object> getPostListPage(Model model, @RequestParam(required = false, defaultValue = "1") Integer page, 
    		@RequestParam(required = false, defaultValue = "10") Integer size, @RequestParam("pageNumber") Integer pageNumber) throws Exception {
    	page = pageNumber;
    	model.addAttribute("resultMap", service.findAll(page, size));

    	return service.findAll(page, size);
    }
    
    @GetMapping(path="/view/{id}")
    public PostResponse getPostViewPage(Model model, PostRequest postRequest, @PathVariable Long id) throws Exception {
    	try {
    		if (postRequest.getId() != null) {
    			model.addAttribute("info", service.findById(postRequest.getId()));
    		}
    	} catch (Exception e) {
    		throw new Exception(e.getMessage());
		}
    	return service.findById(postRequest.getId());
    }
    
    @PostMapping(path="/write")
    public void postWriteAction(Model model, PostRequest postRequest) throws Exception {
    	service.save(postRequest);
    }
    
    @PutMapping(path="/update/{id}")
    public void updateById(Model model, @RequestBody PostRequest postRequest, @PathVariable Long id) throws Exception {
    	service.updatePost(postRequest, id);
    }
    
    
    @DeleteMapping(path="/delete/{id}")
    public void deleteById(Model model, @PathVariable Long id) throws Exception {
    	service.deleteById(id);
    }

}
