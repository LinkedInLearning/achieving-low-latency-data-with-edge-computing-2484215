package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.generator.runner;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import nyla.solutions.core.patterns.creational.Creator;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * EdgeGeneratorRunner
 *
 * @author Gregory Green
 */
@Component
public class VitalStatsGeneratorRunner implements ApplicationRunner
{
    private final Creator<VitalStat> creator;
    private final Consumer<VitalStat> consumer;

    public VitalStatsGeneratorRunner(Creator<VitalStat> creator, Consumer<VitalStat> consumer)
    {
        this.creator = creator;
        this.consumer = consumer;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        run();
    }

    @Scheduled(cron = "* * * * * ?")
    private void run()
    {
        consumer.accept(creator.create());
    }
}
