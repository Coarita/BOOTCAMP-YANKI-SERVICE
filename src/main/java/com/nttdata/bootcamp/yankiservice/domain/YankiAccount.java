package com.nttdata.bootcamp.yankiservice.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class YankiAccount{

	private String id;
	private String identityNumber;
	private String identityType;
	private String mobilePhoneNumber;
	private String mobilePhoneCompany;
	private String imeiNumber;
	private String email;
	private BigDecimal amount;
	
}
