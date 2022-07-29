package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.runner;

import com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.geode.VitalOverviewGeodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * VitalOverviewGeneratorRunner
 *
 * @author Gregory Green
 */
@Component
public class VitalOverviewGeneratorRunner implements ApplicationRunner
{
    private final int count;
    private final VitalOverviewGeodeGenerator generator;

    public VitalOverviewGeneratorRunner(VitalOverviewGeodeGenerator generator, @Value("${vitals.count}") int count)
    {
        this.count = count;
        this.generator = generator;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        generator.generateOverviews(count);

    }
}
