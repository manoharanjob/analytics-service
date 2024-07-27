package com.blockstats.analytics.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "coins")
public class Coin {
	
	@Id
	private String _id;
	private String coinId;
	private String name;
	private String symbol;
	private Double price;
	private Double priceChangePercent24h;
	private LocalDateTime persistedAt;
	private String priceCurrency;
	
}
