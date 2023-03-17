package com.omo.dto.post;

import java.time.LocalDateTime;
import com.omo.entity.post.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private Long id;                       // PK
    private String title;                  // 제목
    private String content;                // 내용
    private String registerId;                 // 작성자
    private int readCnt;                       // 조회 수
    private LocalDateTime registerTime;   // 생성일시
    private LocalDateTime updateTime;
	
	public PostResponse(Post entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.registerId = entity.getRegisterId();
		this.readCnt = entity.getReadCnt();
		this.registerTime = entity.getRegisterTime();
		this.updateTime = entity.getUpdateTime();
	}
	
	@Override
	public String toString() {
		return "PostList [id=" + id + ", title=" + title + ", content=" + content + ", readCnt=" + readCnt
				+ ", registerId=" + registerId + ", registerTime=" + registerTime + ", updateTime=" + updateTime + "]";
	}
	
    
}
