package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.transformation.VitalStatsToBytes;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VitalStatsToBytesTest
{

    private VitalStat expected = JavaBeanGeneratorCreator.of(VitalStat.class).create();

    @Test
    void apply() throws JsonProcessingException
    {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        var subject = new VitalStatsToBytes();
        var actual = subject.apply(expected);

        assertNotNull(actual);

        assertEquals(expected, mapper.readValue(new String(actual),VitalStat.class));
    }
}