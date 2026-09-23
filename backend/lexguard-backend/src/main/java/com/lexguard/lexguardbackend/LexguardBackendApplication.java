package com.lexguard.lexguardbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LexguardBackendApplication {

	public static void main(String[] args) {
		System.err.println("=== ENV TEST ===");
		System.err.println("DATABASE_URL present: " + (System.getenv("DATABASE_URL") != null));
		System.err.println("DB_USERNAME present: " + (System.getenv("DB_USERNAME") != null));
		System.err.println("DB_PASSWORD present: " + (System.getenv("DB_PASSWORD") != null));

		SpringApplication.run(LexguardBackendApplication.class, args);

	}

}
