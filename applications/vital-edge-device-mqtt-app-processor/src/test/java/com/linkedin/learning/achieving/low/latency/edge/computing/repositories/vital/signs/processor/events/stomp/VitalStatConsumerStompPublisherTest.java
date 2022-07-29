package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.stomp;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VitalStatConsumerStompPublisherTest
{
    @Mock
    private  SimpMessageSendingOperations messageTemplate;
    private VitalStat vitalStat = JavaBeanGeneratorCreator.of(VitalStat.class).create();
    private String destination = "test";

    @Test
    void accept()
    {

        var subject = new VitalStatConsumerStompPublisher(messageTemplate, destination);

        subject.accept(vitalStat);

        verify(messageTemplate).convertAndSend(anyString(),any(Object.class));
    }
}