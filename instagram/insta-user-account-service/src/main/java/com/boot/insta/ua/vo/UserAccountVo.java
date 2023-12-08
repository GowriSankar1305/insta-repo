package com.boot.insta.ua.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserAccountVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2818837301831123158L;
	
	private String accountId;
	private String isDeleted;
	private String followersCount;
	private String followingCount;
	private String postsCount;
	private String accountType;
}
