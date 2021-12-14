package com.nttdata.bootcamp.yankiservice.infrastructure.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nttdata.bootcamp.yankiservice.application.model.YankiRepository;
import com.nttdata.bootcamp.yankiservice.infrastructure.repository.YankiCrudRepository;

@Configuration
public class SpringConfiguration {
	
	@Bean
	public YankiRepository repository() {
		return new YankiCrudRepository();
	}

}
