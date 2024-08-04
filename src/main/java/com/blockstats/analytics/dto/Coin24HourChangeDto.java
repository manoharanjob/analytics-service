package com.blockstats.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coin24HourChangeDto {
	
	private String name;
	private String coinImgUrl;
	private Double coin24HrChange;
	
}
