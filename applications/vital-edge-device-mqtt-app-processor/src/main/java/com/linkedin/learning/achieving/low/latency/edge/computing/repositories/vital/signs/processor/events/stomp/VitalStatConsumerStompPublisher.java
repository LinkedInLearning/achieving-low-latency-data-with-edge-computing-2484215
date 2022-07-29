package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.stomp;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * VitalStatConsumerStompPublisher
 *
 * @author Gregory Green
 */
@Component("VitalStatConsumerStompPublisher")
public class VitalStatConsumerStompPublisher implements Consumer<VitalStat>
{
    private final SimpMessageSendingOperations messageTemplate;
    private final String destination;

    public VitalStatConsumerStompPublisher(SimpMessageSendingOperations messageTemplate,
                                           @Value("${vitals.vitalStomp.destination}")String destination)
    {
        this.messageTemplate = messageTemplate;
        this.destination = destination;
    }

    @Override
    public void accept(VitalStat vitalStat)
    {
        this.messageTemplate.convertAndSend(destination,vitalStat);
    }
}
