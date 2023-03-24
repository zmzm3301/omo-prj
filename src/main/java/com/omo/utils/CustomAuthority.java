package com.omo.utils;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CustomAuthorityDeserializer.class)
public class CustomAuthority implements GrantedAuthority {
    private String authority;
    
    public CustomAuthority() {
        super();
    }

    public CustomAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
