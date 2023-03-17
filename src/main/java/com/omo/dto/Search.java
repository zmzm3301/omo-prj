package com.omo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Search {
	private String username;
	private String name;
	private String birth;
	private String newPassword;
	private String confirmNewPassword;
}
