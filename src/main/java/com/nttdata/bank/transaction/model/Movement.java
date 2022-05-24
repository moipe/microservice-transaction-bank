package com.nttdata.bank.transaction.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "movements")
public class Movement {
	
	@Id
	private String _id;
	
		/* CATEGORY */
	//1 - Transacción
	//2 - Transferencia
	private Byte category;
	
		/* TYPE */
	//1 - Depósito
	//2 - Retiro
	//3 - Transferencia
	private Byte type;
	
	private String description;
	
	private Double amount;
	
	private String createAt;
	
	private String externalAccount;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String accountId;

}
