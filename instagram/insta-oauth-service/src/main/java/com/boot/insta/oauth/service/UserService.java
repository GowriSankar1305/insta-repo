package com.boot.insta.oauth.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

import com.boot.insta.oauth.dto.ApiResponseDto;
import com.boot.insta.oauth.dto.AuthRequestDto;
import com.boot.insta.oauth.dto.UserDto;
import com.boot.insta.oauth.exception.PrivateKeyNotFoundException;

public interface UserService {
	public UserDto getUserByUserName(String userName);
	public ApiResponseDto saveUser(UserDto userDto);
	public UserDto getUserById(Long userId);
	public List<UserDto> getUsers();
	public Map<String, String> loginUser(AuthRequestDto dto) throws NoSuchAlgorithmException, InvalidKeySpecException, PrivateKeyNotFoundException;
}
