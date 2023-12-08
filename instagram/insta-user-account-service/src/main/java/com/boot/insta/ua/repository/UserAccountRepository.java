package com.boot.insta.ua.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.insta.ua.entity.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

}
