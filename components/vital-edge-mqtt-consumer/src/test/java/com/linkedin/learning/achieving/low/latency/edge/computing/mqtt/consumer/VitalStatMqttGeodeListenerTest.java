package com.linkedin.learning.achieving.low.latency.edge.computing.mqtt.consumer;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalStatRepository;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VitalStatMqttGeodeListenerTest
{
    @Mock
    private VitalStatRepository vitalStatRepository;

    @Mock
    private Function<byte[], VitalStat> toVitalStat;

    @Mock
    private MqttMessage payload;

    @Test
    void givenVitalStat_When_Arrived_then_Save() throws Exception
    {

        VitalStat vitalStat = JavaBeanGeneratorCreator.of(VitalStat.class).create();
        when(toVitalStat.apply(any())).thenReturn(vitalStat);

        var subject = new VitalStatMqttGeodeListener(vitalStatRepository,toVitalStat);


        String topic = "hello";

        subject.messageArrived(topic,payload);

        verify(toVitalStat).apply(any());
        verify(vitalStatRepository).save(any());

    }
}