package com.boot.insta.oauth.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CacheUtil {
	
	@Autowired
	private CacheManager cacheManager;
	
	private static final String CACHE_NAME = "tokencache";
	
	public Boolean addTokenToCache(String authToken,String userName,String loginToken)	{
	//	log.info("----------------- in cache util ----------------");
		Cache cache = cacheManager.getCache(CACHE_NAME);
		log.info("--------------------adding usr token to cahce----------------");
		cache.put(userName + ":" + loginToken, authToken);
		return cache.get(userName + ":" + loginToken).get() != null;
	}
	
	public Boolean isTokenBlackListed(String userName,String loginToken)	{
		log.info("checking the token with key--->> {}",userName+":"+loginToken);
		boolean result = true;
		Cache cache = cacheManager.getCache(CACHE_NAME);
		if(cache != null)	{
			log.info("========== tokencache is not null ===========");
			ValueWrapper wrapper = cache.get(userName + ":" + loginToken);
			if(wrapper != null)	{
				log.info("========== tokencache wrapper is not null ===========");
				result = (wrapper.get() != null);
				log.info("result-------> {}",result);
			}
		}
		return result;
	}
}
