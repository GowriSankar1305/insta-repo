package com.boot.insta.auth.server.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.boot.insta.auth.server.constants.AppConstants;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_user",schema = AppConstants.DB_SCHEMA)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_generator")
	@SequenceGenerator(name = "user_generator",allocationSize = 1,
	initialValue = 1,schema = AppConstants.DB_SCHEMA,sequenceName = "seq_user")
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "user_profile_id",nullable = false,unique = true)
	private Long userProfileId;
	@Column(name = "user_account_id",nullable = false,unique = true)
	private Long userAccountId;
	@Column(name = "user_name",nullable = false,unique = true)
	private String userName;
	@Column(name = "password",nullable = false)
	private String password;
	@Column(name = "created_time",nullable = false)
	private LocalDateTime createdTime;
	@Column(name = "modified_time",nullable = false)
	private LocalDateTime modifiedTime;
	
}
