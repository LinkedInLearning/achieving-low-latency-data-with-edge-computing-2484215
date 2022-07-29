package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStatName;
import nyla.solutions.core.patterns.creational.generator.FixedNameCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VitalCreatorTest
{
    private VitalCreator subject;
    private int minVitalId = 4;
    private int maxVitalId = 5;
    private String urlPrefix = "http://prefix/";

    private VitalStatName vitalStateName = new VitalStatName();
    private VitalStatName[] stats = {vitalStateName};
    private int minLocationCode = 10001;
    private int maxLocationCode = 10003;

    @Mock
    private FixedNameCreator fullNameCreator;

    @BeforeEach
    void setUp()
    {
        vitalStateName.setStatName("heartRate");
        vitalStateName.setLabel("Heart Rate");
        subject = new VitalCreator(fullNameCreator, minVitalId,maxVitalId,urlPrefix,stats,minLocationCode,maxLocationCode);
    }

    @Test
    void create()
    {
        var expected = "Hello";
        when(fullNameCreator.create()).thenReturn(expected);
        var actual = subject.create();
        assertNotNull(actual);
        assertThat(actual.getId()).matches("4|5");
        assertEquals(expected,actual.getName());
        var actualStats = actual.getStats();
        assertEquals(1,actualStats.length);

        assertTrue(actual.getLocation() != null && actual.getLocation().length() > 0, "Has Location" );
    }

    @Test
    void setId()
    {
        String expected = "expected";
        subject.setId(expected);
        assertEquals(expected,subject.create().getId());
    }
}