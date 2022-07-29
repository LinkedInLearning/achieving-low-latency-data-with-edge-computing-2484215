package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.rules.VitalRuleService;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.alarms.AlarmDataService;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.apache.geode.cache.Operation;
import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VitalAlertAsyncEventListenerTest
{
    @Mock
    private Consumer<VitalAlert> vitalAlertConsumer;

    @Mock
    private AlarmDataService alarmDataService;


    @Mock
    private Operation queryOperation;

    @Mock
    private Operation baseOperation;

    @Mock
    private AsyncEvent event;

    @Mock
    private Consumer<VitalStat> vitalStatConsumer;

    @Mock
    private VitalRuleService vitalRuleService;

    @Test
    void processEvent()
    {
        var vitalStat = JavaBeanGeneratorCreator.of(VitalStat.class).create();

        when(vitalRuleService.isAbnormal(any())).thenReturn(true);
        when(event.getDeserializedValue()).thenReturn(vitalStat);

        var subject = new VitalAlertAsyncEventListener(vitalAlertConsumer,vitalRuleService,vitalStatConsumer, alarmDataService);
        List<AsyncEvent> events = Collections.singletonList(event);

        subject.processEvents(events);

        verify(vitalAlertConsumer).accept(any());
        verify(vitalStatConsumer).accept(any());
        verify(alarmDataService).processAlarm(any());
    }
}