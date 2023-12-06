package com.boot.insta.up.server.service;

import com.boot.insta.up.server.dto.ApiResponseDto;
import com.boot.insta.up.server.dto.UserAccountDto;

public interface UserAccountService {
	public UserAccountDto fetchUserAccount(Long accountId);
	public ApiResponseDto saveOrUpdateUserAccount(UserAccountDto accountDto);
}
