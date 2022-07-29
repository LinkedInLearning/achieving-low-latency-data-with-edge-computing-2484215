package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.rules.VitalRuleService;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.alarms.AlarmDataService;
import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;

import java.util.List;
import java.util.function.Consumer;

public class VitalAlertAsyncEventListener implements AsyncEventListener
{
    @Override
    public boolean processEvents(List<AsyncEvent> list)
    {

        for (AsyncEvent<String, VitalStat> event: list) {

            VitalStat vitalStat = event.getDeserializedValue();
            this.vitalStatConsumer.accept(vitalStat);

            if(!vitalRuleService.isAbnormal(vitalStat))
                continue;

            var summary = alarmDataService.processAlarm(vitalStat);

            vitalAlertSenderConsumer.accept(new VitalAlert(vitalStat.getId(), vitalStat,summary));
        }
        return true;
    }

    public VitalAlertAsyncEventListener(Consumer<VitalAlert> vitalAlertSenderConsumer,
                                        VitalRuleService vitalRuleService,
                                        Consumer<VitalStat> vitalStatConsumer,
                                        AlarmDataService alarmDataService)
    {
        this.vitalAlertSenderConsumer = vitalAlertSenderConsumer;
        this.vitalRuleService = vitalRuleService;
        this.vitalStatConsumer = vitalStatConsumer;
        this.alarmDataService = alarmDataService;
    }

    private final Consumer<VitalAlert> vitalAlertSenderConsumer;
    private final Consumer<VitalStat> vitalStatConsumer;
    private final VitalRuleService vitalRuleService;
    private final AlarmDataService alarmDataService;
}
