package com.nttdata.bootcamp.yankiservice.application.model;

import com.nttdata.bootcamp.yankiservice.domain.YankiAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface YankiRepository {

	Flux<YankiAccount> getAll();
	Mono<YankiAccount> getId(String id);
	Mono<YankiAccount> saveAccount(YankiAccount account);
	Mono<YankiAccount> updateAccount(String id, Mono<YankiAccount> account);
	Mono<Void> deleteAccount(String id);
	
}
