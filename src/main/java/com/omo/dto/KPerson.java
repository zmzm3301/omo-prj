package com.omo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="kperson")
public class KPerson {
	private String nick_name;
	
	@Id
	@Column(name = "email", unique = true)
	private String email;
	private String authority;
	
	public KPerson() {}
	
	public void update(String email, String authority) {
		this.email = email;
		this.authority = authority;
	}
	
}
