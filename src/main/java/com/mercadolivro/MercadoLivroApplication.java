package com.mercadolivro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MercadoLivroApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadoLivroApplication.class, args);
	}

}
