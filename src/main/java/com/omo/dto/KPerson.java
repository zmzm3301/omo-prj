package com.omo.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="kperson")
public class KPerson {
	private String nick_name;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "email", unique = true)
	private String email;

	private String authority;
	
	@CreatedDate
	private LocalDateTime register_time;
	
	public KPerson() {}
	
	public void setAuthority(String authority) {
		this.authority = "uesr";
	}
	
	public void update(String email, String authority) {
		this.email = email;
		this.authority = authority;
	}
	
}
