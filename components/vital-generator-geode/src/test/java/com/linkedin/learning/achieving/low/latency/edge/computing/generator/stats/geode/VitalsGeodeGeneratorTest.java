package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.geode;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.Vital;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStatName;
import com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.VitalCreator;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import nyla.solutions.core.util.Organizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Test for VitalStatsGenerator
 */
@ExtendWith(MockitoExtension.class)
class VitalsGeodeGeneratorTest
{

    @Mock
    private VitalRepository vitalRepository;


    @Mock
    private Consumer<VitalStat> vitalStatConsumer;

    @Mock
    private Environment environment;

    @Mock
    private VitalCreator vitalCreator;
    private VitalsGeodeGenerator subject;

    private final String statName = "heartRate";

    private final double min = 1.34;
    private final double max = 1.34;

    private final int expectedCount = 10;
    private final int minVitalId = 1;
    private final int maxVitalId = 10;

    @BeforeEach
    void setUp()
    {

        subject = new VitalsGeodeGenerator(vitalRepository,vitalStatConsumer,vitalCreator,environment,minVitalId,maxVitalId);
    }

    @Test
    void generateStatFor()
    {
        when(environment.getRequiredProperty("vital."+statName+".min",Double.class)).thenReturn(min);
        when(environment.getRequiredProperty("vital."+statName+".max",Double.class)).thenReturn(max);

        String vitalId = "0";
        subject.generateStatForVitalId(vitalId,statName);
        verify(vitalStatConsumer).accept(any(VitalStat.class));

    }

    @Test
    void generateStatsForAllVitals()
    {
        var creator = JavaBeanGeneratorCreator.of(Vital.class);
        Collection<Vital> vitals  = Organizer.toList(creator.create(), creator.create());
        VitalStatName vitalStatName = new VitalStatName();
        vitalStatName.setStatName(statName);
        vitalStatName.setLabel(statName);
        VitalStatName[] vitalStatNames = {vitalStatName};

        vitals.forEach(v -> v.setStats(vitalStatNames));

        when(environment.getRequiredProperty("vital."+statName+".min",Double.class)).thenReturn(min);
        when(environment.getRequiredProperty("vital."+statName+".max",Double.class)).thenReturn(max);

        when(vitalRepository.findAll()).thenReturn(vitals);

        subject.generateStatsForAllVitals();

        verify(vitalRepository).findAll();
        verify(vitalStatConsumer,atLeastOnce()).accept(any());
    }

    @Test
    void given_minAndMaxVitalId_whenCreate_ThenVitalId_BetweenMinAndMax()
    {
        var expectedCount = 7;
        var minVitalId = 2;
        var maxVitalId = 2;

        subject = new VitalsGeodeGenerator(vitalRepository,vitalStatConsumer,vitalCreator,environment,minVitalId,maxVitalId);

        var actual = subject.generateVital(expectedCount);

        System.out.println(actual);
        assertNotNull(actual);

        assertEquals(expectedCount,actual.size());

    }

    @Test
    void generateVital()
    {

        Vital expectedVital = JavaBeanGeneratorCreator.of(Vital.class).create();

        when(vitalCreator.create()).thenReturn(expectedVital);

        var actual = subject.generateVital(expectedCount);

        assertNotNull(actual);
        assertEquals(expectedCount,actual.size());

        for (int expectedId = 1;expectedId<= expectedCount;expectedId++) {
            var vital = actual.get(expectedId-1);
            assertNotNull(vital);
            assertEquals(expectedVital.getId(),vital.getId());
            assertEquals(expectedVital.getLocation(),vital.getLocation());
            assertEquals(expectedVital.getStats(),vital.getStats());
            assertEquals(expectedVital.getName(),vital.getName());
            assertEquals(expectedVital.getUrl(),vital.getUrl());
        }

        verify(vitalCreator,times(expectedCount)).setId(anyString());
        verify(vitalRepository,times(expectedCount)).save(any());

    }


    @Test
    void generateVital_WhenExists_ThenDoNotSave()
    {
        var count = 2;
        Vital foundVital = JavaBeanGeneratorCreator.of(Vital.class).create();

        when(vitalRepository.findById(any()))
                .thenReturn(Optional.of(foundVital))
                .thenReturn(Optional.empty());

        subject.generateVital(count);

        verify(vitalRepository,atMostOnce()).save(any());
    }

    @Test
    void createVitalStat_ThenValueNotNull()
    {
        String expectedVitalId = "myVital";
        String expectedStatName = "myStat";

        var actual = subject.createVitalStat(expectedVitalId,expectedStatName);

        assertEquals(expectedVitalId,actual.getVitalId());
        assertEquals(expectedStatName,actual.getStatName());
    }
}