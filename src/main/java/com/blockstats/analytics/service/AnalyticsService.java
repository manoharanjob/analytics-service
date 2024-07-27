package com.blockstats.analytics.service;

import java.util.List;

import com.blockstats.analytics.dto.UserAssetDto;
import com.blockstats.analytics.dto.UserSummaryDto;

/**
 * This interface provide abstraction of analytical operations
 * 
 */
public interface AnalyticsService {

	List<UserAssetDto> fetchUserAssetsByUserId(String userId);
	
	List<UserSummaryDto> fetchUserSummaryByUserId(String userId);

}
