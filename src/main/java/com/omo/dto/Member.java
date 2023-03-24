package com.omo.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.omo.utils.CustomAuthority;
import com.omo.utils.CustomAuthorityDeserializer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member implements UserDetails {
	private static final long serialVersionUID = 1L;

 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="member_no" ,updatable = false, unique = true, nullable = false)
	private Long no;

    @Column(updatable = false, nullable = false)
    private String username;
 
    @Column(nullable = false)
    private String password;
    
    private String name;
    
    private String nickname;
    
    private LocalDate birth;
    
    private String sex;
    
    private String createdAt;

    private String updatedAt;
    
    @Column(nullable = false, columnDefinition = "int(5) default '0'")
    private Integer passwordwrong = 0;
    
    @Column(name = "refreshToken")
    private String refreshtoken;
 
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.DETACH, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();





    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    private List<String> roles = new ArrayList<>();

    @Override   //사용자의 권한 목록 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(CustomAuthority::new)
                .collect(Collectors.toList());
    }

    
    @Column(name = "activated")
    private boolean activated;

 
    @Override
    public String getUsername() {
        return username;
    }
 
    @Override
    public String getPassword() {
        return password;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }


}