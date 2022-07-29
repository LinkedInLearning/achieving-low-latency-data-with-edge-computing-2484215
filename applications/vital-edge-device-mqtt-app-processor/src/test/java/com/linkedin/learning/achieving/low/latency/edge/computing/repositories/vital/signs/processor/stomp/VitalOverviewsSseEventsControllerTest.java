package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.stomp;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.sse.VitalOverviewsSseEventsController;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.vital.VitalDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VitalOverviewsSseEventsControllerTest
{

    @Mock
    private VitalDataService vitalDataService;

    private VitalOverviewsSseEventsController subject;

    @BeforeEach
    void setUp()
    {
        subject = new VitalOverviewsSseEventsController(vitalDataService);
    }

    @Test
    void vitalOverviewEvents()
    {

        var actual = subject.streamVitalOverviewEvents();
        assertNotNull(actual);
        actual.blockFirst();

        verify(vitalDataService,atLeastOnce()).findVitalOverviews();
    }
}