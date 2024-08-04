package com.blockstats.analytics.service;

import com.blockstats.analytics.entity.Coin;

/**
 * This interface provide abstraction of coin service operations
 * 
 */
public interface CoinService {

	Coin findBySymbol(String symbol);

}
