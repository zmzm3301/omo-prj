package com.omo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
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
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="member_no" ,updatable = false, unique = true, nullable = false)
	private Long no;

    @Column(updatable = false, unique = true, nullable = false)
    private String username;
 
    @Column(nullable = false)
    private String password;
    
    private String name;
    
    private LocalDate birth;
    
    private String sex;
    
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    @Column(nullable = false, columnDefinition = "int(5) default '0'")
    private Integer passwordwrong = 0;
    
    @Column(name = "refreshToken")
    private String refreshtoken;
 


    @OneToMany(mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @ElementCollection
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add( () -> ("ROLE_USER"));
		
		return authorities;
	}


    
    @Column(name = "activated")
    private boolean activated;
    
//    public Member(Long no) {
//    	this.no = no;
//    }

 
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