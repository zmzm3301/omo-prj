package com.omo.dto.post;

import com.omo.entity.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {
	private Long id;             // PK
    private String title;        // 제목
    private String content;      // 내용
    private String registerId;       // 작성자
    
	public Post toEntity() {
		return Post.builder()
				.title(title)
				.content(content)
				.registerId(registerId)
				.build();
	}

}
