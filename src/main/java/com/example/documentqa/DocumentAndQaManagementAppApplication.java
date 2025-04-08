package com.example.documentqa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DocumentAndQaManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentAndQaManagementAppApplication.class, args);
	}

}
