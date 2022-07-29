package com.linkedin.learning.achieving.low.latency.edge.computing.services;

import com.linkedin.learning.achieving.low.latency.edge.computing.services.vital.VitalDataService;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.alerts.repositories.VitalAlertRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalSummary;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalOverviewRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import nyla.solutions.core.patterns.expression.bre.BusinessRuleEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VitalDataServiceTest
{
    @Mock
    private BusinessRuleEngine<Double,Boolean> businessRuleEngine;

    @Mock
    private VitalRepository vitalRepository;

    @Mock
    private VitalOverviewRepository vitalOverviewRepository;

    @Mock
    private VitalAlertRepository vitalAlertRepository;

    private VitalStat vitalStat = JavaBeanGeneratorCreator.of(VitalStat.class).create();
    private VitalSummary vitalSummary = JavaBeanGeneratorCreator.of(VitalSummary.class).create();

    private VitalDataService subject;


    @BeforeEach
    void setUp()
    {
        subject = new VitalDataService(vitalRepository,vitalOverviewRepository,vitalAlertRepository);
    }

    @Test
    void findVitalOverviewById()
    {
        String vitalId = "";
        subject.findVitalOverviewById(vitalId);
        verify(vitalOverviewRepository).findById(anyString());
    }

    @Test
    void findVitalById()
    {
        String vitalId = "";
        subject.findVitalById(vitalId);
        verify(vitalRepository).findById(anyString());
    }


    @Test
    void findVitalOverviews()
    {
        var actual = subject.findVitalOverviews();

        assertNotNull(actual);
        verify(vitalOverviewRepository).findAll();
    }
}