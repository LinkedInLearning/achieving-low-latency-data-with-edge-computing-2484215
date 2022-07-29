package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VitalMqttGeodeSinkApp
{
	public static void main(String[] args) {
		SpringApplication.run(VitalMqttGeodeSinkApp.class, args);
	}
}
