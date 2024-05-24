package com.nicmora.itemmanagerspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ItemManagerSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemManagerSpringApplication.class, args);
	}

}
