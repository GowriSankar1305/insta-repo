package com.boot.insta.up.server.controller;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.insta.up.server.dto.ApiResponseDto;
import com.boot.insta.up.server.dto.UserAccountDto;
import com.boot.insta.up.server.service.UserAccountService;
import com.boot.insta.up.server.service.UserProfileService;

@RequestMapping("/account/")
@RestController
public class UserAccountController {
	
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserProfileService userProfileService;
	
	@PostMapping("getDetails")
	public ResponseEntity<?> getAccountDetails(
						@RequestHeader String userAccountId,
						@RequestHeader String userProfileId)	{
		if(!NumberUtils.isNumber(userAccountId))	{
			return new ResponseEntity<>(
					new ApiResponseDto("Invalid account id!", 400),HttpStatus.BAD_REQUEST);
		}
		UserAccountDto accDto = userAccountService.fetchUserAccount(Long.parseLong(userAccountId));
		if(accDto == null)	{
			return new ResponseEntity<>(
					new ApiResponseDto("User account not found!", 400),HttpStatus.BAD_REQUEST);
		}
		else	{
			accDto.setAccountHolder(userProfileService.findUser(Long.parseLong(userProfileId)));
			return new ResponseEntity<>(accDto,HttpStatus.OK);
		}
	}
	
}
