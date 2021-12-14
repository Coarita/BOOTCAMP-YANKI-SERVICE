package com.nttdata.bootcamp.yankiservice.application.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nttdata.bootcamp.yankiservice.application.YankiOperations;
import com.nttdata.bootcamp.yankiservice.application.model.YankiRepository;
import com.nttdata.bootcamp.yankiservice.domain.YankiAccount;
import com.nttdata.bootcamp.yankiservice.infrastructure.spring.config.CacheConfig;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class YankiOperationsImpl implements YankiOperations{
	
	@Autowired
	YankiRepository repository;

	@Override
	public Flux<YankiAccount> queryAll() {
		return repository.getAll();
	}

	@Override
	@Cacheable(cacheNames = CacheConfig.USER_CACHE, unless = "#result == null")
	public Mono<YankiAccount> findId(String id) {
		return repository.getId(id);
	}

	@Override
	public Mono<YankiAccount> create(YankiAccount account) {
		return repository.saveAccount(account);
	}

	@Override
	@CachePut(cacheNames = CacheConfig.USER_CACHE, key = "#id", unless = "#result == null")
	public Mono<YankiAccount> update(String id, Mono<YankiAccount> account) {
		return repository.updateAccount(id, account);
	}

	@Override
	@CacheEvict(cacheNames = CacheConfig.USER_CACHE, key = "#id")
	public Mono<Void> delete(String id) {
		return repository.deleteAccount(id);
	}

}
