package com.nttdata.bank.transaction.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bank.transaction.model.Movement;

import reactor.core.publisher.Flux;

@Repository
public interface MovementRepository extends ReactiveMongoRepository<Movement, String>{
	
	Flux<Movement> findMovementByAccountId(String accountId);

}
