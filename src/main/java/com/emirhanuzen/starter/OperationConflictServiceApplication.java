package com.emirhanuzen.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EntityScan(basePackages="com.emirhanuzen")
@ComponentScan(basePackages="com.emirhanuzen")
@EnableJpaRepositories(basePackages="com.emirhanuzen")
@SpringBootApplication
public class OperationConflictServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperationConflictServiceApplication.class, args);
	}

}
