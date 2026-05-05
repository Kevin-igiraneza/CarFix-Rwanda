package com.carfix.carfixrwanda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EnableJpaAuditing
public class CarfixrwandaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarfixrwandaApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataFixer(JdbcTemplate jdbcTemplate) {
		return args -> {
			jdbcTemplate.execute("UPDATE service_requests SET hidden_by_customer = false WHERE hidden_by_customer IS NULL");
			jdbcTemplate.execute("UPDATE service_requests SET hidden_by_mechanic = false WHERE hidden_by_mechanic IS NULL");
		};
	}
}
