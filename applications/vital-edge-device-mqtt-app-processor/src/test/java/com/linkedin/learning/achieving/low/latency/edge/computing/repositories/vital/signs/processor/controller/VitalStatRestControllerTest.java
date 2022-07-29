package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.controller;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;

class VitalStatRestControllerTest
{
    @Test
    void saveVitalStat()
    {
        var vitalStat = JavaBeanGeneratorCreator.of(VitalStat.class)
                .create();
    }
}