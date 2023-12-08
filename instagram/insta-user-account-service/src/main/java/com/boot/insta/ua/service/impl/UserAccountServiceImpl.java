package com.boot.insta.ua.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.boot.insta.ua.dto.ApiResponseDto;
import com.boot.insta.ua.dto.UserAccountDto;
import com.boot.insta.ua.entity.UserAccount;
import com.boot.insta.ua.repository.UserAccountRepository;
import com.boot.insta.ua.service.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	private UserAccountRepository userAccRepo;

	@Override
	public UserAccountDto fetchUserAccount(Long accountId) {
		Optional<UserAccount> usrAccOptl = userAccRepo.findById(accountId);
		return usrAccOptl.isPresent() ? populateAccountDto(usrAccOptl.get()) : null;
	}
	
	private UserAccountDto populateAccountDto(UserAccount userAccount)	{
		UserAccountDto accDto = new UserAccountDto();
		accDto.setAccountId(userAccount.getAccountId());
		accDto.setAccountType(userAccount.getAccountType().name());
		accDto.setFollowersCount(!CollectionUtils.isEmpty(userAccount.getFollowers()) 
				? userAccount.getFollowers().size() : 0L );
		accDto.setFollowingCount(!CollectionUtils.isEmpty(userAccount.getFollowing()) 
				? userAccount.getFollowing().size() : 0L);
		accDto.setPostsCount(!CollectionUtils.isEmpty(userAccount.getPosts()) 
				? userAccount.getPosts().size() : 0L);
		accDto.setIsDeleted(userAccount.getIsDeleted());
		return accDto;
	}
	
	@Override
	public ApiResponseDto saveOrUpdateUserAccount(UserAccountDto accountDto) {
		return null;
	}
}
