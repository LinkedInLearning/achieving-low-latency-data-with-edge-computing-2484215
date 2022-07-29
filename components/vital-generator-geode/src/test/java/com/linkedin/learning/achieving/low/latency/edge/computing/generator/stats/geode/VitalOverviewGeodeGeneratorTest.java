package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.geode;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalOverviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VitalOverviewGeodeGeneratorTest
{
    @Mock
    private VitalOverviewRepository repository;

    @Test
    void given_count_whenGenerate_ThenEquals_AndDataSaved()
    {
        var expectedCount = 10;

        int minVitalId = 2;
        int maxVitalId = 3;
        var subject = new VitalOverviewGeodeGenerator(repository, minVitalId, maxVitalId);

        var actual = subject.generateOverviews(expectedCount);

        assertNotNull(actual);

        System.out.println(actual);
        assertEquals(expectedCount,actual.size());

        for (int i=0;i<expectedCount;i++)
        {
            var actualVitalOverview = actual.get(i);
            assertThat(actualVitalOverview.getId()).matches("2|3");
            assertEquals(0,actualVitalOverview.getPriority());
            assertEquals(0,actualVitalOverview.getAlarmCount());
        }


        verify(repository,times(expectedCount)).save(any());

    }
}