package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.controller;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalStatRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WriteVitalStatControllerTest
{
    @Mock
    private VitalStatRepository vitalStatRepository;

    @Test
    void saveVitStat()
    {
        var subject = new WriteVitalStatController(vitalStatRepository);
        var vitalStat = JavaBeanGeneratorCreator.of(VitalStat.class).create();
        subject.saveVitalStat(vitalStat);
        verify(vitalStatRepository).save(any());
    }
}