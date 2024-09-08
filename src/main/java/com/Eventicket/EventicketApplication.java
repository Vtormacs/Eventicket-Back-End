package com.Eventicket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EventicketApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventicketApplication.class, args);
	}

}
