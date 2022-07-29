package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.stomp;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.sse.ConditionSummariesSseEventsController;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.alarms.AlarmDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConditionSummariesSseEventsControllerTest
{

    @Mock
    private AlarmDataService alarmDataService;

    private ConditionSummariesSseEventsController subject;

    @BeforeEach
    void setUp()
    {
        subject = new ConditionSummariesSseEventsController(alarmDataService);
    }


    @Test
    void vitalSummaryEvents()
    {
        var subject = new ConditionSummariesSseEventsController(alarmDataService);

        var actual = subject.streamConditionCountEvents();

        assertNotNull(actual);
        actual.blockFirst();

        verify(alarmDataService,atLeastOnce()).calculateConditionCounts();
    }
}