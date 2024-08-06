package com.gk.my_secret_review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MySecretReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySecretReviewApplication.class, args);
	}

}
