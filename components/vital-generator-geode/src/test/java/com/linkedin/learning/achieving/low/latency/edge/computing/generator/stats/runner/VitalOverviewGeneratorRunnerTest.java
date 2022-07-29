package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.runner;

import com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.geode.VitalOverviewGeodeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

@ExtendWith({MockitoExtension.class})
class VitalOverviewGeneratorRunnerTest
{
    @Mock
    private VitalOverviewGeodeGenerator generator;

    @Mock
    private ApplicationArguments args;

    @Test
    void when_run_then_call_generator() throws Exception
    {

        int expectedCount = 10;
        var subject = new VitalOverviewGeneratorRunner(generator,expectedCount);

        subject.run(args);

        verify(generator).generateOverviews(anyInt());
    }
}