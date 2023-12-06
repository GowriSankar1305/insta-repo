package com.boot.insta.up.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.insta.up.server.entity.UserProfile;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long>	{
	UserProfile findByEmailId(String emailId);
	UserProfile findByMobileNumber(String mobileNumber);
}
