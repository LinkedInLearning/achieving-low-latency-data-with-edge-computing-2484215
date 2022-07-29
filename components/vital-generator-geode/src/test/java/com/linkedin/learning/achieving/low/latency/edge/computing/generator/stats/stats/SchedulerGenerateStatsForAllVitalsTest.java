package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.stats;

import com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.geode.VitalsGeodeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SchedulerGenerateStatsForAllVitalsTest
{
    @Mock
    private VitalsGeodeGenerator generator;

    @Test
    void given_generator_when_schedule_then_generate()
    {
        var subject = new SchedulerGenerateStatsForAllVitals(generator);

        subject.scheduleGeneratorStats();

        verify(generator).generateStatsForAllVitals();
    }
}