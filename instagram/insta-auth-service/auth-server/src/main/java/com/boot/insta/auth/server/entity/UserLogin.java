package com.boot.insta.auth.server.entity;

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

import com.boot.insta.auth.server.constants.AppConstants;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_user_login",schema = AppConstants.DB_SCHEMA)
public class UserLogin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "userlogin_generator")
	@SequenceGenerator(name = "userlogin_generator",allocationSize = 1,
	initialValue = 1,sequenceName = "seq_user_login",schema = AppConstants.DB_SCHEMA)
	private Long loginId;
	@Column(name = "user_name",nullable = false)
	private String userName;
	@Column(nullable = false,name = "is_login_success")
	private Boolean isLoginSuccess;
	@Column(name = "ip_address",nullable = false)
	private String ipAddress;
	@Column(name = "user_agent",nullable = false) 
	private String userAgent;
	@Column(name = "referer_url",nullable = false)
	private String refererUrl;
	@Column(name = "origin_url",nullable = false)
	private String originUrl;
	@Column(name = "host",nullable = false)
	private String host;
	@Column(name = "platform")
	private String platform;
	@Column(name = "logged_in_time",nullable = false)
	private LocalDateTime loggedInTime;
	@Column(name = "message",nullable = false)
	private String message;
}
