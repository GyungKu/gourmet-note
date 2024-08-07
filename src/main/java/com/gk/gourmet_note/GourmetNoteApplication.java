package com.gk.gourmet_note;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GourmetNoteApplication {

	public static void main(String[] args) {
		SpringApplication.run(GourmetNoteApplication.class, args);
	}

}
