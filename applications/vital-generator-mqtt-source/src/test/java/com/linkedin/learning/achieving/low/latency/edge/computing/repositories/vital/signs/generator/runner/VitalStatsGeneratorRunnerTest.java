package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.generator.runner;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import nyla.solutions.core.patterns.creational.Creator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VitalStatsGeneratorRunnerTest
{
    @Mock
    private ApplicationArguments args;
    @Mock
    private Creator<VitalStat> creator;

    @Mock
    private Consumer<VitalStat> consumer;

    @Test
    void run() throws Exception
    {
        VitalStat expected = mock(VitalStat.class);
        when(creator.create()).thenReturn(expected);
        var subject = new VitalStatsGeneratorRunner(creator,consumer);

        subject.run(args);

        verify(consumer).accept(expected);
    }

}