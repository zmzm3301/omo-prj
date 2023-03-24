package com.omo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.omo.dto.Member;
import com.omo.dto.Post;
import com.omo.repository.MemberRepository;
import com.omo.repository.PostRepository;
import com.omo.utils.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private PostRepository postRepository;
	
	@Transactional
	@Override
	public String add(Post post, Authentication authentication, HttpServletRequest request) {
	        
	        Member author = memberRepository.findByUsername(authentication.getName()).orElse(null); 
	        String tokenWithPrefix = request.getHeader("Authorization");
	        String token = "";
	        if (tokenWithPrefix != null && tokenWithPrefix.startsWith("Bearer ")) {
	            token = tokenWithPrefix.substring(7);
	            // 이제 token 변수에는 접두어 "Bearer "를 제거한 토큰 값이 저장되어 있음
	        }
	        
	        LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedDateTime = now.format(formatter);

	        System.out.println(token);
	        if(jwtTokenProvider.validateToken(token)) {
	        
		    	Post posts = Post.builder()
		    			.title(post.getTitle())
		    			.content(post.getContent())
		    			.author(author)
		    			.createdAt(formattedDateTime)
		    			.notice(post.isNotice())
		    			.build();
		    	
		    	postRepository.save(posts);
		    	return "게시글 등록이 완료되었습니다.";
	        }else {
	        	return "게시글 등록에 실패했습니다.";
	        }
	    }
	
	@Override
	public List<Post> list() {
		List<Post> list = postRepository.findAllOrderByNoticeAndIdDesc();
		System.out.println(list);
		return list;
	}
	
	@Override
	public Post detail(Post no) {
		Long id = no.getId();
		Post detail = postRepository.findById(id).orElse(null);
		
		Member member = memberRepository.findById(detail.getAuthor().getNo()).orElse(null);
		detail.setUsername(member.getUsername());
		
		
		return detail;
	}

	@Override
	public Post delete(Post no, Authentication authentication, HttpServletRequest request) {
		Post post = postRepository.findById(no.getId()).orElse(null);
		Member author = memberRepository.findByNo(post.getAuthor().getNo()).orElse(null);
		String tokenWithPrefix = request.getHeader("Authorization");
        String token = "";
        Post result = new Post();
        
        if (tokenWithPrefix != null && tokenWithPrefix.startsWith("Bearer ")) {
            token = tokenWithPrefix.substring(7);
            // 이제 token 변수에는 접두어 "Bearer "를 제거한 토큰 값이 저장되어 있음
        }
		
        if(jwtTokenProvider.validateToken(token) && authentication.getName().equals(author.getUsername())) {
        	postRepository.deleteById(no.getId());
        	result.setStatus(true);
        	return result;
        }else {
        	return result;
	}
	}
	
	@Transactional
	@Override
	public Post update(Post no, Post newpost, Authentication authentication, HttpServletRequest request) {
	        
	        Member author = memberRepository.findByUsername(authentication.getName()).orElse(null); 
	        Post post = postRepository.findById(no.getId()).orElse(null);
	        String tokenWithPrefix = request.getHeader("Authorization");
	        String token = "";
	        if (tokenWithPrefix != null && tokenWithPrefix.startsWith("Bearer ")) {
	            token = tokenWithPrefix.substring(7);
	            // 이제 token 변수에는 접두어 "Bearer "를 제거한 토큰 값이 저장되어 있음
	        }
	        
	        LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedDateTime = now.format(formatter);

	        Post result = new Post();
	        
	        if(jwtTokenProvider.validateToken(token) && authentication.getName().equals(author.getUsername())) {
	        	post.setTitle(newpost.getTitle());
	        	post.setContent(newpost.getContent());
	        	post.setUpdatedAt(formattedDateTime);
	        	
		    	postRepository.save(post);
		    	result.setStatus(true);
		    	return result;
	        }else {
	        	result.setStatus(false);
	        	return result;
	        }
	    }

	@Override
	public List<Post> myboard(Authentication authentication, HttpServletRequest request) {
		Member author = memberRepository.findByUsername(authentication.getName()).orElse(null);
        String tokenWithPrefix = request.getHeader("Authorization");
        String token = "";
        
        if (tokenWithPrefix != null && tokenWithPrefix.startsWith("Bearer ")) {
            token = tokenWithPrefix.substring(7);
            // 이제 token 변수에는 접두어 "Bearer "를 제거한 토큰 값이 저장되어 있음
        }
        
        if(jwtTokenProvider.validateToken(token)) {
        	List<Post >myPost = postRepository.findAllByAuthor(author);
        	return myPost;
        }
		return null;
	}
	
	
	
	
}
