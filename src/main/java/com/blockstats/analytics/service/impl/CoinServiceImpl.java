package com.blockstats.analytics.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.blockstats.analytics.constant.Constants;
import com.blockstats.analytics.entity.Coin;
import com.blockstats.analytics.repository.CoinRepository;
import com.blockstats.analytics.service.CoinService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class provide implementation of coin service interface
 * 
 */
@Slf4j
@Service
public class CoinServiceImpl implements CoinService {

	private CoinRepository coinRepository;

	public CoinServiceImpl(CoinRepository coinRepository) {
		this.coinRepository = coinRepository;
	}

	@Override
	@Cacheable(value = Constants.COIN, key = "#symbol")
	public Coin findBySymbol(String symbol) {
		List<Coin> coins = coinRepository.findBySymbol(symbol);
		return !coins.isEmpty() ? coins.get(0) : null;
	}

}
