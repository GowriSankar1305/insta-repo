package com.boot.insta.up.server.service;

import java.util.List;

import com.boot.insta.up.server.dto.ApiResponseDto;
import com.boot.insta.up.server.dto.UserProfileDto;

public interface UserProfileService {
	public UserProfileDto findUser(Long id);
	public List<UserProfileDto> findAllUsers();
	public ApiResponseDto saveOrUpdateUser(UserProfileDto userDto);
	public ApiResponseDto saveUser(UserProfileDto profileDto);
	public ApiResponseDto updateUser(UserProfileDto profileDto);
	public UserProfileDto getUserByEmail(String email);
	public UserProfileDto getUserByMobile(String mobile);
	public void deleteUserProfileAndAccount(Long profileId,Long accId);
}
