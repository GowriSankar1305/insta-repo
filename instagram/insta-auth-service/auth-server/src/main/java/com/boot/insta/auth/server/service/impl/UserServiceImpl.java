package com.boot.insta.auth.server.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boot.insta.auth.server.dto.ApiResponseDto;
import com.boot.insta.auth.server.dto.AuthRequestDto;
import com.boot.insta.auth.server.dto.UserDto;
import com.boot.insta.auth.server.entity.User;
import com.boot.insta.auth.server.exception.CustomRunTimeException;
import com.boot.insta.auth.server.exception.PrivateKeyNotFoundException;
import com.boot.insta.auth.server.repository.UserRepository;
import com.boot.insta.auth.server.service.UserLoginService;
import com.boot.insta.auth.server.service.UserService;
import com.boot.insta.auth.server.util.JWTGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder pwdEncoder;
	@Autowired
	private AuthenticationManager authMgr;
	@Autowired
	private JWTGenerator jwtGenerator;
	@Value("classpath:certs/id_rsa_pt.pem")
	private Resource resource;
	
	@Override
	public UserDto getUserByUserName(String userName) {
		UserDto dto = null;
		try	{
			dto = populateDto(userRepo.findByUserName(userName));
		}
		catch(Exception e)	{
			throw new CustomRunTimeException("Problem while fetching user with uname: "+ userName);
		}
		return dto;
	}

	@Override
	public ApiResponseDto saveUser(UserDto userDto) {
		ApiResponseDto dto = null;
		try	{
			User user = userRepo.save(populateEntity(userDto));
			dto = new ApiResponseDto();
			dto.setUserId(user.getUserId().toString());
			dto.setUserName(user.getUserName());
			dto.setUserAccountId(user.getUserAccountId().toString());
			dto.setUserProfileId(user.getUserProfileId().toString());
		}
		catch(Exception e)	{
			throw new CustomRunTimeException("Exception in AuthService while saving user details");
		}
		return dto;
	}

	private User populateEntity(UserDto dto)	{
		User user = null;
		if(null != dto)	{
			user = new User();
			user.setUserName(dto.getUserName());
			user.setPassword(pwdEncoder.encode(dto.getPassword()));
			user.setUserProfileId(Long.valueOf(dto.getUserProfileId()));
			user.setUserAccountId(Long.valueOf(dto.getUserAccId()));
			user.setCreatedTime(LocalDateTime.now());
			user.setModifiedTime(LocalDateTime.now());
		}
		return user;
	}
	
	private UserDto populateDto(User user)	{
		UserDto dto = null;
		if(user != null)	{
			dto = new UserDto();
			if(null != user.getUserId())	{
				dto.setUserId(user.getUserId().toString());
			}
			dto.setUserName(user.getUserName());
		}
		return dto;
	}
	
	@Override
	public UserDto getUserById(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDto> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> loginUser(AuthRequestDto dto)
			throws NoSuchAlgorithmException, InvalidKeySpecException, PrivateKeyNotFoundException {
		Map<String, String> tokenMap = null;
		String key = null;
		Authentication securityPrincipal = authMgr
				.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserName(), dto.getPassword()));
		if (securityPrincipal.isAuthenticated()) {
			tokenMap = new HashMap<>();
			key = getPrivateKey();
			if (key == null) {
				throw new PrivateKeyNotFoundException("Unable to fetch private key from file");
			}
			tokenMap.put("token", jwtGenerator.generateJWT(dto.getUserName(), key));
			tokenMap.put("userName", dto.getUserName());
			tokenMap.put("loginToken", UUID.randomUUID().toString());
			User user = userRepo.findByUserName(dto.getUserName());
			tokenMap.put("userAccountId", user.getUserAccountId().toString());
			tokenMap.put("userProfileId", user.getUserProfileId().toString());
		}
		return tokenMap;
	}
	
	private String getPrivateKey() {
		String key = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(resource.getFile()))) {
			key = reader.readLine();
		} catch (FileNotFoundException fne) {
			log.error("<<<<<<<<<<<<<< private key file not found : {}", fne);
		} catch (IOException e) {
			log.error("<<<<<<<<<<<<<< exception while reading from pem file : {}", e);
		}
		return key;
	}
}
