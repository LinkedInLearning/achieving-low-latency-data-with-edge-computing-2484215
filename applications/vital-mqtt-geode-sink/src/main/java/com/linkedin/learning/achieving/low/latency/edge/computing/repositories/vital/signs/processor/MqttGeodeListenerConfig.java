package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor;

import com.linkedin.learning.achieving.low.latency.edge.computing.transformation.BytesToJson;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.alarms.AlarmDataService;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * MqttGeodeListenerConfig
 *
 * @author Gregory Green
 */
@Configuration
@ComponentScan(basePackageClasses = AlarmDataService.class)
public class MqttGeodeListenerConfig
{
    @Value("${vitals.mqtt.topic:vitalAlerts}")
    private final String queueName = "vitalAlerts"; //vitalAlerts

    @Bean
    public IMqttMessageListener vitalStatMqttGeodeListener(AlarmDataService alertDataService, IMqttClient mqttClient) throws MqttException
    {
        Function<byte[], VitalAlert> converter = new BytesToJson<>(VitalAlert.class);
        IMqttMessageListener listener = (topic, msg) -> {
            alertDataService.saveVitalAlert(converter.apply(msg.getPayload()));
        };
        mqttClient.subscribe(queueName,listener);
        return listener;
    }
}
