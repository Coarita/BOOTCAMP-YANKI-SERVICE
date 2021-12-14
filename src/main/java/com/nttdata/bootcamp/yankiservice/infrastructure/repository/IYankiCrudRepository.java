package com.nttdata.bootcamp.yankiservice.infrastructure.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.nttdata.bootcamp.yankiservice.infrastructure.model.dao.YankiAccountDao;

public interface IYankiCrudRepository extends ReactiveCrudRepository<YankiAccountDao, String>{

}
