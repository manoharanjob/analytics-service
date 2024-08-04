package com.blockstats.analytics.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.blockstats.analytics.entity.Coin;

@Repository
public interface CoinRepository extends MongoRepository<Coin, String> {
	
	List<Coin> findBySymbol(String symbol);

}