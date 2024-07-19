package com.blockstats.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAssetDto {
	
	private String name;
	private String valueCurrency;
	private Double assetValue;
	private Double assetPercentage;
}
