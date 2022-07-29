package com.linkedin.learning.achieving.low.latency.edge.computing.repository.consumer.stats;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalStatRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VitalStatsRepositoryConsumerTest
{
    @Mock
    private VitalStatRepository repository;

    @Test
    void accept()
    {
        var subject = new VitalStatsRepositoryConsumer(repository);
        VitalStat expected = JavaBeanGeneratorCreator.of(VitalStat.class).create();
        subject.accept(expected);

        verify(repository,times(1)).save(any());
    }
}