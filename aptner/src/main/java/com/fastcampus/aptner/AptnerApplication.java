package com.fastcampus.aptner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AptnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AptnerApplication.class, args);
	}

}
