package com.linkedin.learning.achieving.low.latency.edge.computing.vital.sign.alerts.events;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import org.apache.geode.cache.query.CqEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class VitalAlertCacheListener
{
    @ContinuousQuery(name = "VitalAlertEvent", query = "select * from /VitalAlert")
    public void processEvent(CqEvent event)
    {
        if (event.getBaseOperation().isDestroy() || event.getQueryOperation().isDestroy())
            return;

        VitalAlert vitalAlert = (VitalAlert) event.getNewValue();
        logger.info("GOT ALERT {}",vitalAlert);

        messageSendingOperations.convertAndSend(destination, vitalAlert);
    }

    private final MessageSendingOperations messageSendingOperations;
    private final String destination;

    @Value("${vital.alert.note}")
    private String alertNote;
    private Logger logger = LoggerFactory.getLogger(VitalAlertCacheListener.class);

    public VitalAlertCacheListener(MessageSendingOperations queue,  @Value("${vitals.topic.destination}") String destination)
    {
        this.messageSendingOperations = queue;
        this.destination = destination;
    }

}
