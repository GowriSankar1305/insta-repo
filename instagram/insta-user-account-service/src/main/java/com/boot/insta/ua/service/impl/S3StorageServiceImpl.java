package com.boot.insta.ua.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.boot.insta.ua.dto.ApiResponseDto;
import com.boot.insta.ua.dto.CustomRequestHeadersDto;
import com.boot.insta.ua.exception.ProfilePicDownloadException;
import com.boot.insta.ua.exception.ProfilePicUploadException;
import com.boot.insta.ua.service.StorageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class S3StorageServiceImpl implements StorageService {
	
	@Autowired
	private AmazonS3 s3Client;
	
	@Value("${aws.s3.bucket-name}")
	private String bucketName;
	
	@Override
	public ApiResponseDto uploadImage(MultipartFile multiPartFile, CustomRequestHeadersDto headers)
			throws ProfilePicUploadException {
		try	{
			File file = multiPartToFile(multiPartFile);
			s3Client.putObject(bucketName, headers.getUserName(), file);
			file.delete();
			return new ApiResponseDto("Picture uploaded successfully!",200);
		}
		catch(IOException ex)	{
			log.error(">>>>>>>>>>>>>>>>>> uploadImage failed: ",ex);
			throw new ProfilePicUploadException(ex.getMessage());
		}
	}

	@Override
	public byte[] downloadImage(CustomRequestHeadersDto headers) throws ProfilePicDownloadException {
		try {
			S3Object picture = s3Client.getObject(bucketName, headers.getUserName());
			S3ObjectInputStream inputStream = picture.getObjectContent();
			return IOUtils.toByteArray(inputStream);
		} catch (IOException ex) {
			log.error(">>>>>>>>>>>>>>>>>> downloadImage failed: ", ex);
			throw new ProfilePicDownloadException(ex.getMessage());
		}
	}

	@Override
	public ApiResponseDto deleteImage(CustomRequestHeadersDto headers) {
		s3Client.deleteObject(bucketName, headers.getUserName());
		return new ApiResponseDto("Picture deleted successfully!",200);
	}
	
	private File multiPartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		fos.close();
		return convertedFile;
	}

}
