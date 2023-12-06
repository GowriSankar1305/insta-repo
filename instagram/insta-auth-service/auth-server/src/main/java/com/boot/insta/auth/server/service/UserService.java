package com.boot.insta.auth.server.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.boot.insta.auth.server.dto.ApiResponseDto;
import com.boot.insta.auth.server.dto.AuthRequestDto;
import com.boot.insta.auth.server.dto.UserDto;
import com.boot.insta.auth.server.exception.PrivateKeyNotFoundException;

public interface UserService {
	public UserDto getUserByUserName(String userName);
	public ApiResponseDto saveUser(UserDto userDto);
	public UserDto getUserById(Long userId);
	public List<UserDto> getUsers();
	public Map<String, String> loginUser(AuthRequestDto dto) throws NoSuchAlgorithmException, InvalidKeySpecException, PrivateKeyNotFoundException;
}
