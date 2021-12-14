package com.nttdata.bootcamp.yankiservice.application;

import com.nttdata.bootcamp.yankiservice.domain.YankiAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface YankiOperations {

	Flux<YankiAccount> queryAll();
	Mono<YankiAccount> findId(String id);
	Mono<YankiAccount> create(YankiAccount account);
	Mono<YankiAccount> update(String id, Mono<YankiAccount> account);
	Mono<Void> delete(String id);
	
}
