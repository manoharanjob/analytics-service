package com.blockstats.analytics.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class used for global error response model
 * 
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	
	private String error;

}
