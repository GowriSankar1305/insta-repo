package com.boot.insta.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.insta.oauth.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUserName(String userName);
}
