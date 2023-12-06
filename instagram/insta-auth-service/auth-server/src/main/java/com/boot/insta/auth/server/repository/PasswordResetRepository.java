package com.boot.insta.auth.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.insta.auth.server.entity.PasswordReset;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {

}
