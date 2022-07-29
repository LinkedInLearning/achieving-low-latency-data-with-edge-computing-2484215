package com.linkedin.learning.achieving.low.latency.edge.computing.transformation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JsonToBytesTest
{

    private VitalStat expected = JavaBeanGeneratorCreator.of(VitalStat.class).create();

    @Test
    void apply() throws JsonProcessingException
    {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        var subject = new JsonToBytes<VitalStat>();
        var actual = subject.apply(expected);

        assertNotNull(actual);

        assertEquals(expected, mapper.readValue(new String(actual),VitalStat.class));
    }
}