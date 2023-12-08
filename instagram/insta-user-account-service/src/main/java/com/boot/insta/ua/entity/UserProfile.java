package com.boot.insta.ua.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.boot.insta.ua.constants.AppConstants;
import com.boot.insta.ua.constants.GenderEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_user_profile",schema = AppConstants.SCHEMA)
public class UserProfile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE
	,generator = "usr_id_generator")
	@SequenceGenerator(name = "usr_id_generator"
	,sequenceName = "seq_user",schema = AppConstants.SCHEMA,allocationSize = 1)
	private Long userProfileId;
	private String fullName;
	@Column(unique = true,nullable = false,name = "email_id")
	private String emailId;
	@Column(unique = true,nullable = false,name = "mobile_number")
	private String mobileNumber;
	@Column(nullable = false,name = "date_of_birth")
	private String dateOfBirth;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,name = "gender")
	private GenderEnum gender;
	@Column(length = 5000,name = "bio")
	private String bio;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_account_id",nullable = false)
	private UserAccount userAccount;
}
