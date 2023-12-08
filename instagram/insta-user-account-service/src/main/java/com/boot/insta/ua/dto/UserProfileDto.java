package com.boot.insta.ua.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.boot.insta.ua.annotation.UniqueEmail;
import com.boot.insta.ua.annotation.UniqueMobile;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserProfileDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4274554335631657927L;
	private String userProfileId;
	@UniqueMobile
	@NotBlank(message = "mobile number is required")
	private String mobileNumber;
	@UniqueEmail
	@NotBlank(message = "email is required")
	@Email(message = "Email is invalid")
	private String emailId;
	@NotBlank(message = "full name is required")
	private String fullName;
	@NotBlank(message = "username is required")
	private String userName;
	@JsonProperty(value = "currentPassword")
	@NotBlank(message = "password is required")
	private String password;
	@NotBlank(message = "confirm password is required")
	private String confirmPassword;
	@NotBlank(message = "date of birth is required")
	private String dateOfBirth;
	@NotBlank(message = "gender is required")
	private String gender;
	private String bio;
	private String accountId;
}
