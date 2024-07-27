package com.blockstats.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDto {
	
	private String name;
	private Double price;
	private Double quantity;
	private Double avgBuyPrice;
	private Double value;
}
