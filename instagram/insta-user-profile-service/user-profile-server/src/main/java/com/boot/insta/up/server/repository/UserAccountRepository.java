package com.boot.insta.up.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.insta.up.server.entity.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

}
