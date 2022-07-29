package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.rules;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import nyla.solutions.core.patterns.expression.bre.BusinessRuleEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VitalRuleServiceTest
{
    @Mock
    private BusinessRuleEngine<Double,Boolean> businessRuleEngine;

    private VitalStat vitalStat = JavaBeanGeneratorCreator.of(VitalStat.class).create();

    private VitalRuleService subject;

    @BeforeEach
    void setUp()
    {
        subject = new VitalRuleService(businessRuleEngine);
    }

    @Test
    void isAbnormal()
    {
        when(businessRuleEngine.applyForRule(anyString(),any())).thenReturn(true);

        assertTrue(subject.isAbnormal(vitalStat));
    }

    @Test
    void when_invalidState_Then_Throw_IllegalArgumentException()
    {
        when(businessRuleEngine.applyForRule(anyString(),any())).thenReturn(null);

        var vitalStat = VitalStat.builder().vitalId("23")
                .id("25")
                                         .statName("invalid")
                                                 .label("label")
                                         .value(23).build();

        assertThrows(IllegalArgumentException.class, () -> subject.isAbnormal(vitalStat));
    }
}