package com.boot.insta.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.insta.auth.server.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUserName(String userName);
}
