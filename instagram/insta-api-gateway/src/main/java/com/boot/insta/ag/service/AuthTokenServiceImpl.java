package com.boot.insta.ag.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.boot.insta.ag.dto.TokenValidationDto;
import com.boot.insta.ag.util.JWTValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthTokenServiceImpl implements AuthTokenService {
	
	@Value("classpath:certs/id_rsa_pb.pem")
	private Resource resource;
	@Autowired
	private JWTValidator jwtValidator;
	
	@Override
	public boolean isAuthTokenValid(TokenValidationDto dto, String reqPath) {
		String publicKey = fetchPublicKey();
		if(null == publicKey)	{
			throw new RuntimeException("Not able to find public key!");
		}
		dto.setPublicKey(publicKey);
		return jwtValidator.validateJWT(dto,reqPath);
	}
	
	private String fetchPublicKey()	{
		String key = null;
		try(BufferedReader reader = new BufferedReader(new FileReader(resource.getFile())))	{
			 key = reader.readLine();
		}
		catch(FileNotFoundException fne)	{
			log.error("<<<<<<<<<<<<<<<<<<<<< public key file not found : {}",fne);
		}
		catch(IOException ie)	{
			log.error("<<<<<<<<<<<<<<<<<<<< problem while reading key : {}",ie);
		}
		return key;
	}
}
