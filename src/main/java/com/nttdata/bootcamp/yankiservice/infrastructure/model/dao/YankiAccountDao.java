package com.nttdata.bootcamp.yankiservice.infrastructure.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "yankiAccount")
public class YankiAccountDao implements Serializable{
	
	@Id
	private String id;
	private String identityNumber;
	private String identityType;
	private String mobilePhoneNumber;
	private String mobilePhoneCompany;
	private String imeiNumber;
	private String email;
	private BigDecimal amount;
	
	private static final long serialVersionUID = 1L;

}
