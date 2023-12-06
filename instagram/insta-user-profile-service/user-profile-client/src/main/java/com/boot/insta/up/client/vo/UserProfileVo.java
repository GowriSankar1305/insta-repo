package com.boot.insta.up.client.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserProfileVo implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -3340164318581191775L;
	
	private String userProfileId;
	private String mobileNumber;
	private String emailId;
	private String fullName;
	private String dateOfBirth;
	private String gender;
	private String bio;
}
