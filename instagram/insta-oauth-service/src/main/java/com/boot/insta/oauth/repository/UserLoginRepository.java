package com.boot.insta.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.insta.oauth.entity.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

}
