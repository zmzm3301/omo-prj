package com.omo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
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
@Entity(name="comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
	@JoinColumn(name="member_no")
	private Member author;
	
    @JsonProperty("author_id")
    private String authorId;
    
    @JsonProperty("author_email")
    private String authorEmail;
    
	
    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="post_id")
	private Post post;
	
	private String comment;
	
	private String created_at;
	private String updated_at;
	
	private boolean status;
	
    @PostLoad
    private void populateAuthorId() {
        if (this.author != null) {
            this.authorId = this.author.getNickname();
            this.authorEmail = this.author.getUsername();
        }
    }
	

	
}
