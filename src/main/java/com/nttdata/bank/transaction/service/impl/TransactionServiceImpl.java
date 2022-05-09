package com.nttdata.bank.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bank.transaction.model.Transaction;
import com.nttdata.bank.transaction.repository.TransactionRepository;
import com.nttdata.bank.transaction.service.TransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired 
	private TransactionRepository transactionRepository;

	@Override
	public Flux<Transaction> findByAccountId(String accountId) {
		return transactionRepository.findTransactionByAccountId(accountId);
	}

	@Override
	public Mono<Transaction> findById(String id) {
		return transactionRepository.findById(id);
	}

	@Override
	public Mono<Transaction> save(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	public Mono<Transaction> update(Transaction transaction) {
		
		Mono<Transaction> oTransaction = transactionRepository.findById(transaction.get_id());
		
		return oTransaction.flatMap( transactions -> {
										transactions.setType(transaction.getType());
										transactions.setAmount(transaction.getAmount());
										return transactionRepository.save(transactions);
									 }
			
							);
	}

}
