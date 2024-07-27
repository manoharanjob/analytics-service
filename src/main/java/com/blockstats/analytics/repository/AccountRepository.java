package com.blockstats.analytics.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.blockstats.analytics.dto.UserSummaryDto;
import com.blockstats.analytics.entity.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

	@Aggregation(pipeline = {
			"{ $match: { userId: ?0 } }",
			"{ $lookup: { from: 'coins', localField: 'name', foreignField: 'symbol', as: 'coin' } }",
			"{ $unwind: '$coin' }",
			"{ $group: { _id: '$name', name: { $first: '$name' } quantity: { $sum: '$quantity' }, price: { $first: '$coin.price' } } }",
			"{ $project: { name: 1, quantity: 1, price: 1, value: { $multiply: ['$quantity', '$price'] } } }" })
	List<UserSummaryDto> fetchUserSummaryByUserId(String userId);
}