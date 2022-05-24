package com.nttdata.bank.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bank.transaction.client.AccountClientRest;
//import com.nttdata.bank.transaction.client.ProductClientRest;
import com.nttdata.bank.transaction.model.Movement;
import com.nttdata.bank.transaction.repository.MovementRepository;
import com.nttdata.bank.transaction.service.MovementService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService{
	
	@Autowired 
	private MovementRepository movementRepository;
	
	@Autowired
	private AccountClientRest accountClientRest;
	
	/*@Autowired
	private ProductClientRest productClientRest;*/

	@Override
	public Flux<Movement> findByAccountId(String accountId) {
		return movementRepository.findMovementByAccountId(accountId);
	}

	@Override
	public Mono<Movement> findById(String id) {
		return movementRepository.findById(id)
				.switchIfEmpty(Mono.error(new Exception("No existe un movimiento con el id: " + id)));
	}

	@Override
	public Mono<Movement> save(Movement movement) {
		Mono<Movement> movementMono = Mono.just(movement);
		return movementMono.flatMap(m -> {
			Mono<Movement> oMovementMono = Mono.empty();
			if(m.getCategory() == 1) {
				
					/*Validarción de maximos de transacciones*/
				/*oMovementMono = movementRepository.findMovementByAccountId(m.getAccountId())
				.filter(mv -> mv.getCategory() == 1)
				.collectList().flatMap(movementList -> {
					return numberTransactions(m.getAccountId())
					.flatMap(p -> {
						Mono<Movement> mvnMono = Mono.empty();
						if(m.getType() == 1) {
							if(movementList.size() > p.getMaxTransactions()) {
								m.setAmount(m.getAmount() - p.getCommissionTransaction());
							}
							mvnMono = accountClientRest.updateBalance(m.getAccountId(), m.getAmount(), (byte) 1)
													.flatMap(a -> movementRepository.save(movement));
						}
						if(m.getType() == 2) {
							if(movementList.size() > p.getMaxTransactions()) {
								m.setAmount(m.getAmount() + p.getCommissionTransaction());
							}
							
							mvnMono = accountClientRest.updateBalance(m.getAccountId(), m.getAmount(), (byte) 2)
													.switchIfEmpty(Mono.error(new Exception("No tiene saldo suficiente.")))
													.flatMap(a -> movementRepository.save(movement));
						}
						return mvnMono;
					});
				});*/
			
				if(m.getType() == 1) {
					oMovementMono = accountClientRest.updateBalance(m.getAccountId(), m.getAmount(), (byte) 1)
											.flatMap(a -> movementRepository.save(movement));
				}
				if(m.getType() == 2) {
					oMovementMono = accountClientRest.updateBalance(m.getAccountId(), m.getAmount(), (byte) 2)
											.switchIfEmpty(Mono.error(new Exception("No tiene saldo suficiente.")))
											.flatMap(a -> movementRepository.save(movement));
				}
			}
			if(m.getCategory() == 2) {
				if(m.getType() == 3) {
					oMovementMono = accountClientRest.updateBalance(m.getAccountId(), m.getAmount(), (byte) 2)
											.switchIfEmpty(Mono.error(new Exception("No tiene saldo suficiente para realizar la transferencia.")))
											.flatMap(a -> {
												return accountClientRest.updateBalance(m.getExternalAccount(), m.getAmount(), (byte) 1)
													.flatMap(ac -> {
														Movement oMovement = new Movement();
														oMovement.setAccountId(m.getExternalAccount());
														oMovement.setAmount(m.getAmount());
														oMovement.setCategory(m.getCategory());
														oMovement.setCreateAt(m.getCreateAt());
														oMovement.setDescription("Depósito");
														oMovement.setExternalAccount(m.getAccountId());
														oMovement.setType(m.getType());
														return movementRepository.save(oMovement);
													}).flatMap(aco -> movementRepository.save(m));
											});
				}
			}
			
			return oMovementMono;
		});
	}

	@Override
	public Mono<Movement> update(Movement movement) {
		Mono<Movement> movementMono = movementRepository.findById(movement.get_id());
		return movementMono.flatMap( m -> {
										m.setType(movement.getType());
										m.setAmount(movement.getAmount());
										return movementRepository.save(m);
									 }
			
							);
	}
	
		/*Retorna un producto*/
	/*public Mono<Product> numberTransactions(String accountId) {		
	 return accountClientRest.findByid(accountId)
	 	.flatMap(a -> productClientRest.findById(accountId));
	}*/

}
