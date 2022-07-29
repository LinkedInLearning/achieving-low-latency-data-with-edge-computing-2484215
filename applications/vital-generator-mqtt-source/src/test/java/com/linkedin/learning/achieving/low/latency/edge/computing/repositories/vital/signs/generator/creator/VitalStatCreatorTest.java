package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.generator.creator;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStatName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VitalStatCreatorTest
{

    @Test
    void create()
    {

        int minVitalId = 3;
        int maxVitalId = 4;
        String statName = "hello";
        String label = "hello";
        double min = 34.3;
        double max = 44.3;

        VitalStatName vitalStateNames = new VitalStatName(statName,label,min,max);
        var subject = new VitalStatCreator(minVitalId,maxVitalId, vitalStateNames);
        var actual = subject.create();
        assertNotNull(actual);
        assertThat(actual.getVitalId()).matches("3|4");
    }
}