package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VitalGeneratorMqttSourceApp
{
	public static void main(String[] args) {
		SpringApplication.run(VitalGeneratorMqttSourceApp.class, args);
	}
}
