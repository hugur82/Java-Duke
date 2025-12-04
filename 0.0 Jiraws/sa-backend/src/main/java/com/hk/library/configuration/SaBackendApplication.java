package com.hk.library.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EntityScan("com.hk.library.Book.model")
@EnableJpaRepositories(basePackages = "com.hk.library.Book.persistence")
@SpringBootApplication(scanBasePackages = {"com.hk.library.*"})
public class SaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaBackendApplication.class, args);
	}

}
