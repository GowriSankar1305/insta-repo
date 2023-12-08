package com.boot.insta.ua.controller;

import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boot.insta.ua.dto.ApiResponseDto;
import com.boot.insta.ua.dto.UserProfileDto;
import com.boot.insta.ua.service.StorageService;
import com.boot.insta.ua.service.UserProfileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/user/")
public class UserProfileController {
	
	@Autowired
	private UserProfileService userService;
	@Autowired
	private StorageService storageService;
	
	@PostMapping("get-user")
	public ResponseEntity<?> getUser(@RequestBody Map<String, String> reqPayload)	{
		if(!NumberUtils.isNumber(reqPayload.get("userId")))	{
			return new ResponseEntity<>(new ApiResponseDto("Invalid user id!",400), HttpStatus.BAD_REQUEST);
		}
		Long userId = Long.parseLong(reqPayload.get("userId"));
		log.info("UserController.getUser() userId------> {}",userId);
		UserProfileDto dto = userService.findUser(userId);
		if(null != dto)	{
			return new ResponseEntity<>(dto, HttpStatus.OK); 
		}
		else	{
			return new ResponseEntity<>(new ApiResponseDto("User not found!",400), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("save-or-update")
	public ResponseEntity<ApiResponseDto> saveOrUpdateUser(@Valid @RequestBody UserProfileDto userDto)	{
		return new ResponseEntity<ApiResponseDto>(userService.saveOrUpdateUser(userDto),HttpStatus.OK);
	}
	
	@GetMapping("fetch-yrs-mnths")
	public ResponseEntity<Map<String, List<?>>> populateYearsAndMnths()	{
		Map<String, List<?>> dobMap = new HashMap<>();
		dobMap.put("months", Stream.of(Month.values())
				.map(month -> month.name()).collect(Collectors.toList()));
		dobMap.put("years", IntStream.range(1930, Year.now()
				.getValue() + 1).boxed().collect(Collectors.toList()));
		return new ResponseEntity<Map<String,List<?>>>(dobMap,HttpStatus.OK);
	}
	
	@PostMapping("fetch-days")
	public ResponseEntity<Map<String, List<Integer>>> 
	populateDaysByMnth(@RequestBody Map<String, String> reqPayload)	{
		String month = reqPayload.get("month");
		Map<String, List<Integer>> daysMap = new HashMap<>();
		daysMap.put("days", IntStream.range(1, Month.valueOf(month).length(Year.now().isLeap()) + 1)
				.boxed().collect(Collectors.toList()));
		return new ResponseEntity<Map<String,List<Integer>>>(daysMap,HttpStatus.OK);
	}
	
	@PostMapping("profile-pic/upload")
	public ResponseEntity<ApiResponseDto> uploadProfilePicture(MultipartFile file)	{
		return new ResponseEntity<ApiResponseDto>(new ApiResponseDto(),HttpStatus.OK);
	}
}
