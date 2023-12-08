package com.boot.insta.ua.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.boot.insta.ua.constants.AppConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_user_profile_picture",schema = AppConstants.SCHEMA)
public class UserProfilePicture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "picture_id_generator")
	@SequenceGenerator(name = "picture_id_generator",initialValue = 1,
	allocationSize = 1,schema = AppConstants.SCHEMA,sequenceName = "seq_picture_id_generator")
	@Column(name = "picture_id")
	private Long pictureId;
	@Column(name = "picture_name",nullable = false)
	private String pictureName;
	@Column(name = "picture_size",nullable = false)
	private String pictureSize;
	@Column(name = "picture_type",nullable = false)
	private String pictureType;
	@Column(name = "created_time",nullable = false)
	private LocalDateTime createdTime;
	@Column(name = "modified_time",nullable = false)
	private LocalDateTime modifiedTime;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_profile_id",nullable = false)
	private UserProfile userProfile;
}
