package com.boot.insta.ua.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.boot.insta.ua.authservice.feignclient.AuthServiceClient;
import com.boot.insta.ua.constants.AccountTypeEnum;
import com.boot.insta.ua.constants.GenderEnum;
import com.boot.insta.ua.dto.ApiResponseDto;
import com.boot.insta.ua.dto.UserProfileDto;
import com.boot.insta.ua.entity.UserAccount;
import com.boot.insta.ua.entity.UserProfile;
import com.boot.insta.ua.repository.UserAccountRepository;
import com.boot.insta.ua.repository.UserProfileRepository;
import com.boot.insta.ua.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private UserProfileRepository userProfileRepo;
	@Autowired
	private AuthServiceClient authClient;
	@Autowired
	private UserAccountRepository userAccRepo;
	
	@Override
	public UserProfileDto findUser(Long id) {
		Optional<UserProfile> usrOpl = userProfileRepo.findById(id);
		return usrOpl.isPresent() ? prepareUserDto.apply(usrOpl.get()) : null;
	}
	
	private Function<UserProfile, UserProfileDto> prepareUserDto = user -> {
		UserProfileDto dto = new UserProfileDto();
		dto.setEmailId(user.getEmailId());
		dto.setFullName(user.getFullName());
		dto.setMobileNumber(user.getMobileNumber());
		dto.setUserProfileId(user.getUserProfileId().toString());
		dto.setBio(user.getBio());
		dto.setAccountId(user.getUserAccount().getAccountId().toString());
		return dto;
	};
	
	@Override
	public List<UserProfileDto> findAllUsers() {
		return userProfileRepo.findAll().stream()
				.map(prepareUserDto).collect(Collectors.toList());
	}

	@Override
	public ApiResponseDto saveOrUpdateUser(UserProfileDto userDto) {
		if(StringUtils.hasText(userDto.getUserProfileId()))	{
			return updateUser(userDto);
		}
		return saveUser(userDto);
	}

	@Override
	public ApiResponseDto saveUser(UserProfileDto profileDto) {
		UserProfile userProfile = new UserProfile();
		UserAccount userAcc = new UserAccount();
		ApiResponseDto apiResponse = new ApiResponseDto();

		userAcc.setAccountType(AccountTypeEnum.PUBLIC);
		userAcc.setIsDeleted(Boolean.FALSE);
		userAcc.setCreatedTime(LocalDateTime.now());
		userAcc.setModifiedTime(LocalDateTime.now());
		userAcc = userAccRepo.save(userAcc);

		userProfile.setEmailId(profileDto.getEmailId());
		userProfile.setFullName(profileDto.getFullName());
		userProfile.setMobileNumber(profileDto.getMobileNumber());
		userProfile.setGender(GenderEnum.valueOf(profileDto.getGender()));
		userProfile.setDateOfBirth(profileDto.getDateOfBirth());
		userProfile.setUserAccount(userAcc);
		userProfile = userProfileRepo.save(userProfile);

		/*** call auth service to save user details ***/
		Map<String, String> reqPayload = new HashMap<>();
		reqPayload.put("userName", profileDto.getUserName());
		reqPayload.put("password", profileDto.getPassword());
		reqPayload.put("userProfileId", userProfile.getUserProfileId().toString());
		reqPayload.put("userAccId", userAcc.getAccountId().toString());
		ResponseEntity<?> response = authClient.saveUserDetails(reqPayload,
				userProfile.getUserProfileId().toString(),userAcc.getAccountId().toString());
		log.info("auth service resp--> {}", response.getStatusCode());
		apiResponse.setMessage("User successfully created!");
		apiResponse.setStatusCode(200);
		return apiResponse;
	}
	
	public void deleteUserProfileAndAccount(Long profileId,Long accId)	{
		userProfileRepo.deleteById(accId);
		userAccRepo.deleteById(profileId);
	}

	@Override
	public ApiResponseDto updateUser(UserProfileDto profileDto) {
		return null;
	}

	@Override
	public UserProfileDto getUserByEmail(String email) {
		return populateDto(userProfileRepo.findByEmailId(email));
	}

	@Override
	public UserProfileDto getUserByMobile(String mobile) {
		return populateDto(userProfileRepo.findByMobileNumber(mobile));
	}
	
	public static UserProfileDto populateDto(UserProfile entity)	{
		UserProfileDto dto = null;
		if(null != entity)	{
			dto = new UserProfileDto();
			if(null != entity.getUserAccount())	{
				dto.setAccountId(entity.getUserAccount().getAccountId().toString());				
			}
			dto.setBio(entity.getBio());
			dto.setDateOfBirth(entity.getDateOfBirth());
			dto.setEmailId(entity.getEmailId());
			dto.setFullName(entity.getFullName());
			dto.setGender(entity.getGender().name());
			dto.setMobileNumber(entity.getMobileNumber());
			dto.setUserProfileId(entity.getUserProfileId().toString());
		}
		return dto;
	}
}
