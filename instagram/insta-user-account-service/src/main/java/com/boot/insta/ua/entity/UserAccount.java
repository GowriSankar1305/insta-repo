package com.boot.insta.ua.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.boot.insta.ua.constants.AccountTypeEnum;
import com.boot.insta.ua.constants.AppConstants;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_user_account",schema = AppConstants.SCHEMA)
public class UserAccount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "user_acc_gen")
	@SequenceGenerator(name = "user_acc_gen",
	allocationSize = 1,sequenceName = "seq_user_account",schema = AppConstants.SCHEMA)
	private Long accountId;
	
	@ElementCollection
	@CollectionTable(name = "tbl_user_account_followers",
	joinColumns = @JoinColumn(name="user_account_id"),schema = AppConstants.SCHEMA)
	@Column(name = "follower_id")
	private Set<Long> followers;
	
	@ElementCollection
	@CollectionTable(name = "tbl_user_accounts_following",
	joinColumns = @JoinColumn(name="user_account_id"),schema = AppConstants.SCHEMA)
	@Column(name = "following_id")
	private Set<Long> following;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type",nullable = false)
	private AccountTypeEnum accountType;
	
	@ElementCollection
	@CollectionTable(name = "tbl_user_account_posts",
	joinColumns = @JoinColumn(name = "user_account_id"),schema = AppConstants.SCHEMA)
	@Column(name = "post_id")
	private Set<Long> posts;
	
	@Column(name = "created_time",nullable = false)
	private LocalDateTime createdTime;
	
	@Column(name = "modified_time",nullable = false)
	private LocalDateTime modifiedTime;
	
	@Column(name = "is_deleted",nullable = false)
	private Boolean isDeleted;
}
