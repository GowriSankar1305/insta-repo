package com.boot.insta.auth.server.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTGenerator {
	
	long expiryTimeInMs = 300000;
	
	public String generateJWT(String userName,String privateKey) throws NoSuchAlgorithmException,InvalidKeySpecException {
		log.info("<<---------- generating Auth token ---------->>");
		return Jwts.builder()
				.subject(userName)
				.issuedAt(new Date())
				.expiration(new Date(Instant.now().plusMillis(expiryTimeInMs).toEpochMilli()))
				.claim("role", "user")
				.signWith(constructPrivateKey(privateKey), SignatureAlgorithm.RS256)
				.compact();
	}
	
	public PrivateKey constructPrivateKey(String key) throws NoSuchAlgorithmException,InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] keyBytes = Base64.getDecoder().decode(key);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		return keyFactory.generatePrivate(pkcs8KeySpec);
	}
}
