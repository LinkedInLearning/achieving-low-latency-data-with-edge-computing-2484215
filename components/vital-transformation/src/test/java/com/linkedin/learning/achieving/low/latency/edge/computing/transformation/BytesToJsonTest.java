package com.linkedin.learning.achieving.low.latency.edge.computing.transformation;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class BytesToJsonTest
{

    @Test
    void apply()
    {
        var input = "{}".getBytes(StandardCharsets.UTF_8);
        var subject = new BytesToJson<VitalStat>(VitalStat.class);

        VitalStat actual = subject.apply(input);
        assertNotNull(actual);
    }
}