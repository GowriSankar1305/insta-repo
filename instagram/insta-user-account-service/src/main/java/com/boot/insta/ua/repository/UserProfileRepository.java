package com.boot.insta.ua.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.insta.ua.entity.UserProfile;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long>	{
	UserProfile findByEmailId(String emailId);
	UserProfile findByMobileNumber(String mobileNumber);
}
