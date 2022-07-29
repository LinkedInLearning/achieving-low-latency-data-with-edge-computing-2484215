package com.linkedin.learning.achieving.low.latency.edge.computing.services;

import com.linkedin.learning.achieving.low.latency.edge.computing.services.alarms.AlarmDataService;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.alerts.repositories.VitalAlertRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalSummary;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.aggregates.ConditionSummaries;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalOverviewRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlarmDataServiceTest
{
    @Mock
    private VitalOverviewRepository vitalOverviewRepository;

    @Mock
    private VitalRepository vitalRepository;

    @Mock
    private VitalAlertRepository vitalAlertRepository;

    @Mock
    private ApplicationContext applicationContext;

    private VitalSummary vitalSummary = JavaBeanGeneratorCreator.of(VitalSummary.class).create();
    private VitalStat vitalStat = JavaBeanGeneratorCreator.of(VitalStat.class).create();
    private VitalAlert vitalAlert = JavaBeanGeneratorCreator.of(VitalAlert.class).create();


    private AlarmDataService subject;


    @BeforeEach
    void setUp()
    {
        subject = new AlarmDataService();

        subject.setVitalOverviewRepository(vitalOverviewRepository);
        subject.setVitalAlertRepository(vitalAlertRepository);
        subject.setVitalRepository(vitalRepository);

    }


    @Test
    void given_vitalAlert_whenSaveAlert_Then_AlertSavedInRepository()
    {
        subject.saveVitalAlert(vitalAlert);

        verify(vitalAlertRepository).save(any());
    }

    @Test
    void when_calculateConditionCounts_Then_Return_Values()
    {
        int expectedGoodCount = 1;
        int expectedWarningCount = 3;
        int expectedSeriousCount = 5;

        when(vitalOverviewRepository.countByPriority(anyShort())).thenReturn(expectedGoodCount)
                                                                 .thenReturn(expectedWarningCount)
                                                                 .thenReturn(expectedSeriousCount);

        ConditionSummaries summaryCount = subject.calculateConditionCounts();

        assertNotNull(summaryCount);
        assertEquals(expectedGoodCount,summaryCount.getLowCount());
        assertEquals(expectedWarningCount,summaryCount.getWarningCount());
        assertEquals(expectedSeriousCount,summaryCount.getSevereCount());
    }
    @Test
    void processAlarm()
    {

        when(this.vitalOverviewRepository.findById(anyString())).thenReturn(Optional.of(vitalSummary.getVitalOverview()));
        when(this.vitalRepository.findById(anyString())).thenReturn(Optional.of(vitalSummary.getVital()));

        VitalSummary actual = subject.processAlarm(vitalStat);

        assertNotNull(actual.getVitalOverview().getId());
        assertEquals(vitalSummary,actual);
    }


    @Test
    void saveAlarm()
    {

        when(this.vitalOverviewRepository.findById(anyString())).thenReturn(Optional.of(vitalSummary.getVitalOverview()));
        when(this.vitalRepository.findById(anyString())).thenReturn(Optional.of(vitalSummary.getVital()));

        var vitalAlert = new VitalAlert();
        vitalAlert.setId(vitalStat.getId());
        vitalAlert.setVitalStat(vitalStat);

        VitalAlert actual = subject.processAndSaveAlarm(vitalAlert);
        assertNotNull(actual);
        assertNotNull(actual.getId());

        assertEquals(actual.getVitalSummary(),vitalSummary);
        assertEquals(actual.getVitalStat(),vitalStat);
        verify(vitalAlertRepository).save(any());
    }

    @Test
    void processAlarm_WhenAlarmCountLessThanThreshold_ThenPrioritySet()
    {
        long expectedAlarmCount = 2;
        vitalSummary.getVitalOverview().setAlarmCount(expectedAlarmCount);

        when(this.vitalOverviewRepository.findById(anyString())).thenReturn(Optional.of(vitalSummary.getVitalOverview()));
        when(this.vitalRepository.findById(anyString())).thenReturn(Optional.of(vitalSummary.getVital()));



        VitalSummary actualSummary = subject.processAlarm(vitalStat);

        assertNotNull(actualSummary);
        var actualOverview = actualSummary.getVitalOverview();
        assertNotNull(actualOverview);

        assertEquals(1,actualOverview.getPriority());
    }


    @Test
    void calculateConditionCounts()
    {
        int expectedLowCount = 1;
        int expectedWarningCount = 2;
        int expectedSevereCount = 3;
        when(vitalOverviewRepository.countByPriority(any()))
                .thenReturn(expectedLowCount)
                .thenReturn(expectedWarningCount)
                .thenReturn(expectedSevereCount);

        ConditionSummaries expected = ConditionSummaries.builder()
                                                        .lowCount(expectedLowCount)
                                                        .warningCount(expectedWarningCount)
                                                        .severeCount(expectedSevereCount)
                                                        .build();

        var actual = subject.calculateConditionCounts();

        assertEquals(expected,actual);

    }
}