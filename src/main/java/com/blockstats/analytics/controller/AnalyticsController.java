package com.blockstats.analytics.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blockstats.analytics.dto.UserAssetDto;
import com.blockstats.analytics.dto.UserSummaryDto;
import com.blockstats.analytics.service.AnalyticsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * This class expose an API endpoints for Analytics
 *
 */
@RestController
@RequestMapping("/analytics")
@Tag(name = "Analytics Controller", description = "Analytics assets calculation endpoints")
public class AnalyticsController {

	private AnalyticsService analyticsService;

	public AnalyticsController(AnalyticsService analyticsService) {
		this.analyticsService = analyticsService;
	}

	/**
	 * This API fetch user assets values
	 * 
	 * @return List of {@link UserAssetDto}
	 */
	@GetMapping("/assets")
	@Operation(summary = "Fetch user assets value", description = "Calculate user assets value and return")
	public ResponseEntity<List<UserAssetDto>> fetchUserAssetsByUserId(@RequestParam("userId") String userId) {
		return ResponseEntity.ok(analyticsService.fetchUserAssetsByUserId(userId));
	}
	
	/**
	 * This API fetch user summary
	 * 
	 * @return List of {@link UserSummaryDto}
	 */
	@GetMapping("/summary")
	@Operation(summary = "Fetch user assets value", description = "Fetch user summary value and return")
	public ResponseEntity<List<UserSummaryDto>> fetchUserSummaryByUserId(@RequestParam("userId") String userId) {
		return ResponseEntity.ok(analyticsService.fetchUserSummaryByUserId(userId));
	}

}
