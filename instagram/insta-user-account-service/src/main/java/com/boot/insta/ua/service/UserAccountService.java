package com.boot.insta.ua.service;

import com.boot.insta.ua.dto.ApiResponseDto;
import com.boot.insta.ua.dto.UserAccountDto;

public interface UserAccountService {
	public UserAccountDto fetchUserAccount(Long accountId);
	public ApiResponseDto saveOrUpdateUserAccount(UserAccountDto accountDto);
}
