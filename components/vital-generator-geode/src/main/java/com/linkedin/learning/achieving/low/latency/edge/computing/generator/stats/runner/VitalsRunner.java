package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.runner;

import com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.geode.VitalsGeodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * VitalsRunner
 *
 * @author Gregory Green
 */
@Component
public class VitalsRunner implements CommandLineRunner
{
    private final VitalsGeodeGenerator generator;
    private final int count;

    public VitalsRunner(VitalsGeodeGenerator generator, @Value("${vitals.count}") int count)
    {
        this.generator = generator;
        this.count = count;
    }


    @Override
    public void run(String... args) throws Exception
    {
        generator.generateVital(count);

    }
}
