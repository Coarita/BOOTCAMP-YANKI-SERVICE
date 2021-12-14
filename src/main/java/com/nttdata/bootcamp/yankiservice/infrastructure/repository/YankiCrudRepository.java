package com.nttdata.bootcamp.yankiservice.infrastructure.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nttdata.bootcamp.yankiservice.application.model.YankiRepository;
import com.nttdata.bootcamp.yankiservice.domain.YankiAccount;
import com.nttdata.bootcamp.yankiservice.infrastructure.model.dao.YankiAccountDao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class YankiCrudRepository implements YankiRepository{

	@Autowired
	IYankiCrudRepository repository;
	
	@Override
	public Flux<YankiAccount> getAll() {
		return repository.findAll()
				.map(this::mapYankiAccountDaoToYankiAccount);
	}

	@Override
	public Mono<YankiAccount> getId(String id) {
		return repository.findById(id)
				.map(this::mapYankiAccountDaoToYankiAccount);
	}

	@Override
	public Mono<YankiAccount> saveAccount(YankiAccount account) {
		return repository.save(mapYankiAccountToYankiAccountDao(account))
				.map(this::mapYankiAccountDaoToYankiAccount);
	}

	@Override
	public Mono<YankiAccount> updateAccount(String id, Mono<YankiAccount> account) {
		return repository.findById(id)
				.flatMap(p->account.map(this::mapYankiAccountToYankiAccountDao)
						.doOnNext(e->e.setId(id)))
				.flatMap(repository::save)
				.map(this::mapYankiAccountDaoToYankiAccount);
	}

	@Override
	public Mono<Void> deleteAccount(String id) {
		return repository.deleteById(id);
	}
	
	private YankiAccount mapYankiAccountDaoToYankiAccount(YankiAccountDao accountDao) {
		YankiAccount account = new YankiAccount();
		BeanUtils.copyProperties(accountDao, account);
		return account;
	}
	
	private YankiAccountDao mapYankiAccountToYankiAccountDao(YankiAccount account) {
		YankiAccountDao accountDao = new YankiAccountDao();
		BeanUtils.copyProperties(account, accountDao);
		return accountDao;
	}

}
