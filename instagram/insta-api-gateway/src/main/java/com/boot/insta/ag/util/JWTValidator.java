package com.boot.insta.ag.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.boot.insta.ag.dto.TokenValidationDto;
import com.boot.insta.ag.feignclient.AuthFeignClient;
import com.boot.insta.ag.restclient.AUthWebClient;
import com.boot.insta.ag.restclient.AuthServiceClient;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTValidator {
	
	//@Autowired
//	private AuthServiceClient authServiceClient;
//	@Autowired
//	@Lazy
//	private AuthFeignClient authFeignClient;
	@Autowired
	private AUthWebClient webClient;
	
	public boolean validateJWT(TokenValidationDto dto, String reqPath) {
		boolean isValidToken = checkTokenIsValid(dto.getAuthToken(), dto.getPublicKey());
		log.info("is valid token ---------> {}", isValidToken);
		log.info("login req path-----------> {}", reqPath);
		if (isValidToken) {
			log.info("****** tkn is valid ******");
			boolean isTokenBlacklisted = checkTokenIsBlacklisted(dto);
			if (isTokenBlacklisted) {
				log.info("////////////// token is blacklisted ///////////////");
				return false;
			} else {
				return true;
			}
		} else {
			log.info("***** token is not valid *****");
			log.info("////////// invalid jwt ///////////////");
			return false;
		}
	}
	
	private boolean checkTokenIsValid(String token,String publicKey)	{
		log.info("<-------- validating authorization token ------------>");
		boolean flag = false;
		try	{
			JwtParser parser = Jwts.parser().verifyWith(constructPublicKey(publicKey)).build();
			parser.parseSignedClaims(token);
			flag = true;
		}
		catch(SignatureException se)	{
			log.error("<<<<<<<<<<<<<<<<<<< SignatureException: {}",se.getMessage());
		}
		catch(MalformedJwtException mfe)	{
			log.error("<<<<<<<<<<<<<<<<<< MalformedJwtException : {}",mfe.getMessage());
		}
		catch(ExpiredJwtException eje)	{
			log.error("<<<<<<<<<<<<<<<<<< ExpiredJwtException : {}",eje.getMessage());
		}
		catch(UnsupportedJwtException usje)	{
			log.error("<<<<<<<<<<<<<<<<< UnsupportedJwtException : {}",usje.getMessage());
		}
		catch(IllegalArgumentException ile)	{
			log.error("<<<<<<<<<<<<<<<<< IllegalArgumentException : {}",ile.getMessage());
		}
		catch(NoSuchAlgorithmException nsale)	{
			log.error("<<<<<<<<<<<<<<<<< NoSuchAlgorithmException : {}",nsale.getMessage());
		}
		catch(InvalidKeySpecException ike)	{
			log.error("<<<<<<<<<<<<<<<<< InvalidKeySpecException : {}",ike.getMessage());
		}
		return flag;
	}
	
	private boolean checkTokenIsBlacklisted(TokenValidationDto dto) {
		log.info("token dto=======>> {}" , dto.toString());
		Map<String, String> reqHeaders = new HashMap<>();
		reqHeaders.put("Authorization", "Bearer " + dto.getAuthToken());
		reqHeaders.put("loginToken", dto.getLoginToken());
		reqHeaders.put("userName", dto.getUserName());
		//return authFeignClient.isTokenBlacklisted(reqHeaders).getBody();
		//return authServiceClient.checkForBlackListedToken(reqHeaders);
		return webClient.isTokenBlackListed(reqHeaders);
	}
	
	private PublicKey constructPublicKey(String key) throws NoSuchAlgorithmException,InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] keyBytes = Base64.getDecoder().decode(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		return keyFactory.generatePublic(x509KeySpec);
	}
}
