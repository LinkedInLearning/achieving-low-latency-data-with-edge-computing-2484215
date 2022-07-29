package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.stomp;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

/**
 * VitalSignCacheListenerStompPublisher
 *
 * @author Gregory Green
 */
public class VitalSignCacheListenerStompPublisher extends CacheListenerAdapter<String, VitalStat>
{
    private final SimpMessageSendingOperations messageTemplate;
    private final String destination;

    public VitalSignCacheListenerStompPublisher(SimpMessageSendingOperations messageTemplate, String destination)
    {
        this.messageTemplate = messageTemplate;
        this.destination = destination;
    }

    @Override
    public void afterCreate(EntryEvent<String, VitalStat> event)
    {
        this.processEvent(event);
    }

    @Override
    public void afterUpdate(EntryEvent<String, VitalStat> event)
    {
        this.processEvent(event);
    }

    private void processEvent(EntryEvent<String, VitalStat> event)
    {
        var value = event.getNewValue();
        if(value == null)
            return;

        messageTemplate.convertAndSend(destination,event.getNewValue());
    }
}
