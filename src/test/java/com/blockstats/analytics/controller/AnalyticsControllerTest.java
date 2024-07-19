package com.blockstats.analytics.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.blockstats.analytics.dto.UserAssetDto;
import com.blockstats.analytics.exception.handler.GlobalExceptionHandler;
import com.blockstats.analytics.service.AnalyticsService;

@ExtendWith(MockitoExtension.class)
@RunWith(PowerMockRunner.class)
public class AnalyticsControllerTest {

	private MockMvc mockMvc;
	
	@InjectMocks
	private AnalyticsController analyticsController;
	@Mock
	private AnalyticsService analyticsService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(analyticsController).setControllerAdvice(new GlobalExceptionHandler()).build();
	}

	@Test
	public void fetchUserAssetsByUserId_Success() throws Exception {
		List<UserAssetDto> list = new ArrayList<>();
		list.add(new UserAssetDto("ABC", "USD", 5655.99, 12.23));
		when(analyticsService.fetchUserAssetsByUserId(anyString())).thenReturn(list);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/blockstats/aggregator/analytics/assets")
						.param("userId", "123")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
		
		assertNotNull(mvcResult.getResponse());
	}
	
}
