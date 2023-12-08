package com.boot.insta.oauth.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.boot.insta.oauth.constants.AppConstants;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_password_reset",schema = AppConstants.DB_SCHEMA)
public class PasswordReset {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "passwordeset_gen")
	@SequenceGenerator(name = "passwordeset_gen",sequenceName = "seq_password_reset",
	allocationSize = 1,initialValue = 1,schema = AppConstants.DB_SCHEMA)
	private Long resetId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",nullable = false)
	private User user;
	@Column(name = "old_password",nullable = false)
	private String oldPassword;
	@Column(name = "is_reset_success",nullable = false)
	private Boolean isResetSuccess;
	@Column(name = "ip_address",nullable = false)
	private String ipAddress;
	@Column(name = "user_agent",nullable = false)
	private String userAgent;
	@Column(name = "referer_url",nullable = false)
	private String refererUrl;
	@Column(name = "reset_time",nullable = false)
	private LocalDateTime resetTime;
	
}
