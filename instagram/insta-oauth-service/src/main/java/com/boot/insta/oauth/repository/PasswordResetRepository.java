package com.boot.insta.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.insta.oauth.entity.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

}
