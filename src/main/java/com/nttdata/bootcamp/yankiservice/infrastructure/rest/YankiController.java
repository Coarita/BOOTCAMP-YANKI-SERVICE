package com.nttdata.bootcamp.yankiservice.infrastructure.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.yankiservice.application.YankiOperations;
import com.nttdata.bootcamp.yankiservice.domain.YankiAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/yankiaccounts")
public class YankiController {

	@Autowired
	YankiOperations operations;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<Flux<YankiAccount>>> get() {
		return Mono.just(ResponseEntity.ok(operations.queryAll()));
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<YankiAccount>> getId(@PathVariable String id) {
		return Mono.just(id)
				.flatMap(operations::findId)
				.map(ResponseEntity::ok)
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<YankiAccount>> post(@RequestBody YankiAccount entity) {
		return Mono.just(entity)
				.doOnNext(e->e.setId(null))
				.flatMap(operations::create)
				.map(this::postResponse);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<YankiAccount>> put(@PathVariable String id, @RequestBody Mono<YankiAccount> entity){
		return operations.findId(id)
				.flatMap(a->operations.update(id, entity))
				.map(this::postResponse)
				.defaultIfEmpty(ResponseEntity.notFound().build())
				.onErrorReturn(ResponseEntity.badRequest().build());
	}
	
	@PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<ResponseEntity<YankiAccount>> patch(@PathVariable String id, @RequestBody YankiAccount entity){
		return operations.findId(id)
				.doOnNext(a->a.setAmount(entity.getAmount()))
				.flatMap(a->operations.update(id, Mono.just(a)))
				.map(this::postResponse)
				.defaultIfEmpty(ResponseEntity.notFound().build())
				.onErrorReturn(ResponseEntity.badRequest().build());
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
		return operations.findId(id)
				.flatMap(a->operations.delete(a.getId())
						.thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	private ResponseEntity<YankiAccount> postResponse(YankiAccount account){
		return ResponseEntity.created(URI.create("/accounts/" + account.getId())).body(account);
	}

}
