package com.nttdata.bank.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bank.transaction.model.Movement;
import com.nttdata.bank.transaction.service.MovementService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MovementController {
	
	@Autowired
	private MovementService movementService;
	
	/*CIRCUIT BRAKER CONFIGURADO EN EL MISMO MICROSERVICIO TRANSACTION*/
//	@Autowired
//	private ReactiveCircuitBreakerFactory circuitBreakerFactory;
	
	
	@GetMapping
	private Flux<Movement> findByAccountId(@RequestParam String accountId){	
		return movementService.findByAccountId(accountId);
	}
	
	@GetMapping("/{id}")
	private Mono<Movement> findById(@PathVariable String id){
		return movementService.findById(id);
	}
	

	/*CIRCUIT BRAKER CONFIGURADO EN EL MISMO MICROSERVICIO TRANSACTION*/
//	@GetMapping("/{id}")
//	private Mono<Transaction> findById(@PathVariable String id){
//		return circuitBreakerFactory.create("transactions")
//				.run(movementService.findById(id), e -> metodoAlterno(id));
//	}
//	
//	private Mono<Transaction> metodoAlterno(String id){
//		Mono<Transaction> t = Mono.empty();
//		Transaction tra = new Transaction();
//		tra.set_id(id);
//		tra.setAccountId("12345655");
//		tra.setAmount(200D);
//		tra.setType("Retiro");
//		t = Mono.just(tra);
//		return t;
//	}
	
	
	@PostMapping
	private Mono<Movement> save(@RequestBody Movement transaction, @RequestParam String accountId){	
		transaction.setAccountId(accountId);
		return movementService.save(transaction);	
	}
	
	@PutMapping("/{id}")
	private Mono<Movement> update(@PathVariable String id, @RequestBody Movement transaction){
		transaction.set_id(id);
		return movementService.update(transaction);
		
	}

}
