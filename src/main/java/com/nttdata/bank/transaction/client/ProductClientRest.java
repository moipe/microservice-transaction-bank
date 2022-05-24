package com.nttdata.bank.transaction.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.bank.transaction.model.Product;

import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "service-product", url = "localhost:9961")
public interface ProductClientRest {
	
	@GetMapping("/{id}")
	public Mono<Product> findById(@PathVariable String id);

}
