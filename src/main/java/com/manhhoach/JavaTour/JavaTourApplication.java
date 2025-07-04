package com.manhhoach.JavaTour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JavaTourApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaTourApplication.class, args);
	}

}
