package com.boot.insta.ua.service;

import org.springframework.web.multipart.MultipartFile;

import com.boot.insta.ua.dto.ApiResponseDto;
import com.boot.insta.ua.dto.CustomRequestHeadersDto;
import com.boot.insta.ua.exception.ProfilePicDownloadException;
import com.boot.insta.ua.exception.ProfilePicUploadException;

public interface StorageService {
	public ApiResponseDto uploadImage(MultipartFile file,CustomRequestHeadersDto headers) throws ProfilePicUploadException;
	public byte[] downloadImage(CustomRequestHeadersDto headers) throws ProfilePicDownloadException;
	public ApiResponseDto deleteImage(CustomRequestHeadersDto headers);
}
