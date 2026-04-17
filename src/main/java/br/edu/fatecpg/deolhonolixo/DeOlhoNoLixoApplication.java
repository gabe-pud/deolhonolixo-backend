package br.edu.fatecpg.deolhonolixo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.mongodb.autoconfigure.MongoAutoConfiguration;

@SpringBootApplication

public class DeOlhoNoLixoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeOlhoNoLixoApplication.class, args);
	}

}
