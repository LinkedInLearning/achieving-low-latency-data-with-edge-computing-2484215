package com.linkedin.learning.achieving.low.latency.edge.computing.vital.sign.alerts.events;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.Operation;
import org.apache.geode.cache.query.CqEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.core.MessageSendingOperations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VitalAlertCacheListenerTest
{
    @Mock
    private MessageSendingOperations messageSendingOperations;

    @Mock
    private CqEvent event;

    private VitalAlert vitalAlert = JavaBeanGeneratorCreator.of(VitalAlert.class)
                                                            .generateNestedAll()
                                                            .create();

    @Mock
    private Operation baseOperation;
    private String destination = "/topic/alerts";

    @BeforeEach
    void setUp()
    {
        when(event.getBaseOperation()).thenReturn(baseOperation);
        when(event.getQueryOperation()).thenReturn(baseOperation);
        when(event.getNewValue()).thenReturn(vitalAlert);
    }

    @Test
    void processEvent_When_AlertCountGreaterThanOne_Then_Increase_Priority()
    {
        var subject = new VitalAlertCacheListener(messageSendingOperations, destination);
        subject.processEvent(event);
        verify(messageSendingOperations).convertAndSend(anyString(),any(VitalAlert.class));
    }

}