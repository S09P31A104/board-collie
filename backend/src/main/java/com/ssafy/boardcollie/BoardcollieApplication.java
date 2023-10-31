package com.ssafy.boardcollie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BoardcollieApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardcollieApplication.class, args);
	}

}
