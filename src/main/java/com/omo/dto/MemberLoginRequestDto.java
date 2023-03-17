package com.omo.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class MemberLoginRequestDto {
    private String username;
    private String password;
}
