package com.blockstats.analytics.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import com.blockstats.analytics.dto.UserAssetDto;
import com.blockstats.analytics.service.impl.AnalyticsServiceImpl;

@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
public class AnalyticsServiceTest {

	@InjectMocks
	private AnalyticsServiceImpl analyticsService;
	@Mock
	private MongoTemplate mongoTemplate;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void fetchUserAssetsByUserId_Success() {
		List<UserAssetDto> list = new ArrayList<>();
		list.add(new UserAssetDto("ABC", "USD", 5655.99, 12.23));
		AggregationResults<UserAssetDto> aggResults = new AggregationResults(list, new Document());
		when(mongoTemplate.aggregate(any(Aggregation.class), anyString(), UserAssetDto.class)).thenReturn(aggResults);
		
		List<UserAssetDto> result = analyticsService.fetchUserAssetsByUserId("123");

		assertNotNull(result);
	}

}
