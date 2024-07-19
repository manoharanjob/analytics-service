package com.blockstats.analytics.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "accounts")
public class Account {
	
	@Id
	private String _id;
	private String exchange;
	private String name;
	private String userId;
	private String accountId;
	private LocalDateTime persistedAt;
	private Long quantity;
	private Long value;
	private String valueCurrency;
	
}
