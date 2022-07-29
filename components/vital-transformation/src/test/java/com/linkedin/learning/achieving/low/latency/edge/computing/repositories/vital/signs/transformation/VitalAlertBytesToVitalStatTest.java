package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.transformation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VitalAlertBytesToVitalStatTest
{

    private ObjectMapper mapper;

    @BeforeEach
    void setUp()
    {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

    }

    @Test
    void apply() throws JsonProcessingException
    {

        var expected = JavaBeanGeneratorCreator.of(VitalAlert.class).create();
        byte[] bytes = mapper.writeValueAsBytes(expected);
        var subject = new VitalAlertBytesToVitalStat();

        assertEquals(expected.getVitalStat(),subject.apply(bytes));
    }


}