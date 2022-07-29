package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.generator;

import nyla.solutions.core.patterns.creational.generator.FixedNameCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FixedNamedConfig
 *
 * @author Gregory Green
 */
@Configuration
public class FixedNamedConfig
{
    @Value("${vitals.names}")
    private String[] names;

    @Bean
    public FixedNameCreator fixedNameCreator(){
        return new FixedNameCreator(names);
    }
}
