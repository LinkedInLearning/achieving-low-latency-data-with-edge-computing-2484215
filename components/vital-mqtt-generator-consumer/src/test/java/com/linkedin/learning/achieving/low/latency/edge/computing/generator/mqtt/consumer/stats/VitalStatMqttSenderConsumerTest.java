package com.linkedin.learning.achieving.low.latency.edge.computing.generator.mqtt.consumer.stats;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VitalStatMqttSenderConsumerTest
{
    @Mock
    private IMqttClient client;

    @Mock
    private Function<VitalStat, byte[]> converter;

    private String topic = "test";

    byte [] payload = {1,2};

    @Test
    void accept() throws MqttException
    {
        when(converter.apply(any())).thenReturn(payload);
        var subject = new VitalStatMqttSenderConsumer(client, converter, topic);
        VitalStat expected = JavaBeanGeneratorCreator.of(VitalStat.class).create();
        subject.accept(expected);
        verify(client).publish(anyString(), any(MqttMessage.class));
        verify(converter).apply(any());
    }
}