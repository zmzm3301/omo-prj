package com.omo.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="comment")
public class Comment {
	
	@Id
	private int cid;
	private String register_id;
	private String content;
	private int id;
	
	@CreatedDate
	private String register_time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
	
}
