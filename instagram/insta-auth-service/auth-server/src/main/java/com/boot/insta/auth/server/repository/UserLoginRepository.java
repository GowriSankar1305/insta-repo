package com.boot.insta.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.insta.auth.server.entity.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

}
