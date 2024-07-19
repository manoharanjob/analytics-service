package com.blockstats.analytics.service.impl;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.blockstats.analytics.dto.UserAssetDto;
import com.blockstats.analytics.service.AnalyticsService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class provide implementation of analytical service interface
 * 
 */
@Slf4j
@Service
public class AnalyticsServiceImpl implements AnalyticsService {

	private MongoTemplate mongoTemplate;

	public AnalyticsServiceImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<UserAssetDto> fetchUserAssetsByUserId(String userId) {
		// Match Operation
		MatchOperation matchOperation = Aggregation
				.match(new Criteria().andOperator(Criteria.where("userId").is(userId), Criteria.where("value").gt(0)));

		// Group Operation
		GroupOperation groupOperation = Aggregation.group("name").first("name").as("name").first("valueCurrency")
				.as("valueCurrency").sum("value").as("assetValue");

		// Sort Operation
		SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "assetValue");

		// Projection Operation
		ProjectionOperation projectionOperation = Aggregation.project().andInclude("name", "valueCurrency",
				"assetValue");

		Aggregation aggregation = Aggregation.newAggregation(matchOperation, groupOperation, sortOperation,
				projectionOperation);

		// Execute the aggregation
		AggregationResults<UserAssetDto> result = mongoTemplate.aggregate(aggregation, "accounts", UserAssetDto.class);

		List<UserAssetDto> userAssets = result.getMappedResults();
		Double totalAssets = userAssets.stream().mapToDouble(UserAssetDto::getAssetValue).sum();
		return userAssets.stream().map(asset -> {
			Double percentage = ((asset.getAssetValue() * 100) / totalAssets);
			asset.setAssetPercentage(percentage);
			return asset;
		}).sorted(Comparator.comparingDouble(UserAssetDto::getAssetPercentage).reversed()).toList();
	}

}
