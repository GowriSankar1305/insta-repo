package com.boot.insta.ua.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccountDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7082836980087411235L;
	private Long accountId;
	private UserProfileDto accountHolder;
	private Boolean isDeleted;
	private Long followersCount;
	private Long followingCount;
	private Long postsCount;
	private String accountType;
	
}
