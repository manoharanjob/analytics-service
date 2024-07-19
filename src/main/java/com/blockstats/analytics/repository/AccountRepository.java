package com.blockstats.analytics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.blockstats.analytics.entity.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

}