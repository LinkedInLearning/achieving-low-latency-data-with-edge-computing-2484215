package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.stats;

import com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.geode.VitalsGeodeGenerator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * SchedulerGenerateStatsForAllVitals
 *
 * @author Gregory Green
 */
@Component
public class SchedulerGenerateStatsForAllVitals
{
    private final VitalsGeodeGenerator generator;
    public SchedulerGenerateStatsForAllVitals(VitalsGeodeGenerator generator)
    {

        this.generator = generator;
    }

//    @Scheduled(cron = "*/3 * * * * ?")
    public void scheduleGeneratorStats()
    {
        this.generator.generateStatsForAllVitals();
    }
}
