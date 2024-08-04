package com.blockstats.analytics.scheduler;

import java.util.Objects;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.blockstats.analytics.constant.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * This interface provide abstraction of coin service operations
 * 
 */
@Slf4j
@Component
public class CacheRefreshScheduler {
	
	private CacheManager cacheManager;
	
	public CacheRefreshScheduler(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	// Every 5 minutes will clear coin cache
	@Scheduled(fixedRate = 5 * 60 * 1000, initialDelay = 1 * 60 * 1000)
    public void refreshCoinCache() {
		log.info("Clear coin cache");
        Cache cache = cacheManager.getCache(Constants.COIN);
        if(Objects.nonNull(cache)) {
        	cache.clear();
        }
    }

}
