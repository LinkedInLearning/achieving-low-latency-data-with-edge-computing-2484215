package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.stomp;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.stomp.VitalSignCacheListenerStompPublisher;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.EntryEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VitalSignCacheListenerStompPublisherTest
{
    @Mock
    private SimpMessageSendingOperations messageTemplate;

    @Mock
    private EntryEvent<String, VitalStat> event;
    private VitalStat vitalStat;
    private String destination = "/topics/vitalStats";


    @BeforeEach
    void setUp()
    {
        vitalStat = JavaBeanGeneratorCreator.of(VitalStat.class).create();
    }

    @Test
    void afterCreate_When_HasNewValue_Then_ConvertAndSend()
    {
        when(event.getNewValue()).thenReturn(vitalStat);
        var subject = new VitalSignCacheListenerStompPublisher(messageTemplate, destination);

        subject.afterCreate(event);
        verify(messageTemplate).convertAndSend(anyString(), any(VitalStat.class));
    }
    @Test
    void afterCreate_WhenValueIsNull_Then_DoNotConvertAndSend()
    {
        var subject = new VitalSignCacheListenerStompPublisher(messageTemplate, destination);

        subject.afterCreate(event);

        verify(messageTemplate,never()).convertAndSend(anyString(), Mockito.nullable(VitalStat.class));

    }


    @Test
    void afterUpdate_When_HasNewValue_Then_ConvertAndSend()
    {
        when(event.getNewValue()).thenReturn(vitalStat);
        var subject = new VitalSignCacheListenerStompPublisher(messageTemplate, destination);

        subject.afterUpdate(event);

        verify(messageTemplate).convertAndSend(anyString(), any(VitalStat.class));
    }
    @Test
    void afterUpdate_WhenValueIsNull_Then_DoNotConvertAndSend()
    {
        var subject = new VitalSignCacheListenerStompPublisher(messageTemplate, destination);

        subject.afterUpdate(event);

        verify(messageTemplate,never()).convertAndSend(anyString(), Mockito.nullable(VitalStat.class));

    }
}