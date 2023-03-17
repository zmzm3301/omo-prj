package com.omo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class OmoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmoApplication.class, args);
	}

}
