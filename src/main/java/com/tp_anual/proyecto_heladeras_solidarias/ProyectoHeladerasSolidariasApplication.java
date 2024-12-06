package com.tp_anual.proyecto_heladeras_solidarias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ProyectoHeladerasSolidariasApplication {

	public static void main(String[] args) {
		System.setProperty("DB_URL", System.getenv("DB_URL"));
		System.setProperty("DB_USERNAME", System.getenv("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", System.getenv("DB_PASSWORD"));

		SpringApplication.run(ProyectoHeladerasSolidariasApplication.class, args);
	}
}
