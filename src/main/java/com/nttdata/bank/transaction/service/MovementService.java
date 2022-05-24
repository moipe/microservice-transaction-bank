package com.nttdata.bank.transaction.service;

import com.nttdata.bank.transaction.model.Movement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
	
	Flux<Movement> findByAccountId(String accountId);
	
	Mono<Movement> findById(String id);
	
	Mono<Movement> save(Movement movement);
	
	Mono<Movement> update(Movement movement);

}
