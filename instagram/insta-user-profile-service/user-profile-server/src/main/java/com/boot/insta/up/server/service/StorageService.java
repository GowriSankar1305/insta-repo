package com.boot.insta.up.server.service;

import org.springframework.web.multipart.MultipartFile;

import com.boot.insta.up.server.dto.ApiResponseDto;
import com.boot.insta.up.server.dto.CustomRequestHeadersDto;
import com.boot.insta.up.server.exception.ProfilePicDownloadException;
import com.boot.insta.up.server.exception.ProfilePicUploadException;

public interface StorageService {
	public ApiResponseDto uploadImage(MultipartFile file,CustomRequestHeadersDto headers) throws ProfilePicUploadException;
	public byte[] downloadImage(CustomRequestHeadersDto headers) throws ProfilePicDownloadException;
	public ApiResponseDto deleteImage(CustomRequestHeadersDto headers);
}
