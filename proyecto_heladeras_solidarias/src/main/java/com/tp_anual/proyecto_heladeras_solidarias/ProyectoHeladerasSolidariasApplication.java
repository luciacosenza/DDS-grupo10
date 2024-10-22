package com.tp_anual.proyecto_heladeras_solidarias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
public class ProyectoHeladerasSolidariasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoHeladerasSolidariasApplication.class, args);
	}
}
