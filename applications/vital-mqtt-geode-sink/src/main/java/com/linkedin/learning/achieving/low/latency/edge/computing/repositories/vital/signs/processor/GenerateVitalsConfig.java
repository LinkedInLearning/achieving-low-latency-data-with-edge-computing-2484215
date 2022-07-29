package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor;

import com.linkedin.learning.achieving.low.latency.edge.computing.repository.consumer.stats.VitalStatsRepositoryConsumer;
import com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.VitalCreator;
import com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.geode.VitalsGeodeGenerator;
import com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.runner.VitalsRunner;
import nyla.solutions.core.patterns.creational.generator.FixedNameCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * FixedNamedConfig
 *
 * @author Gregory Green
 */
@Configuration
@ComponentScan(basePackageClasses = {
        VitalsRunner.class,
        VitalCreator.class,
        VitalsGeodeGenerator.class,
        VitalStatsRepositoryConsumer.class})
public class GenerateVitalsConfig
{
    @Value("${vitals.names}")
    private String[] names;

    @Bean
    public FixedNameCreator fixedNameCreator(){
        return new FixedNameCreator(names);
    }
}
