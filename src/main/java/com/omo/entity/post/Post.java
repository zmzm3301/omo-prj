package com.omo.entity.post;

import com.omo.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@NoArgsConstructor
@Getter
@Entity(name="tb_post")
public class Post extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	private int readCnt;
	private String registerId;
	
	@Builder
	public Post(Long id, String title, String content, int readCnt, String registerId/*, LocalDateTime registerTime LocalDateTime updateTime*/) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.readCnt = readCnt;
		this.registerId = registerId;
	}
	
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
}
