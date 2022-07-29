package com.linkedin.learning.achieving.low.latency.edge.computing.generator.mqtt.consumer.stats;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * VitalStatMqttSenderConsumer
 *
 * @author Gregory Green
 */
@Component
@Primary
public class VitalStatMqttSenderConsumer implements Consumer<VitalStat>
{
    private final IMqttClient client;
    private final Function<VitalStat,byte[]> converter;
    private final String topic;
    private final Logger log = LoggerFactory.getLogger(VitalStatMqttSenderConsumer.class);

    public VitalStatMqttSenderConsumer(IMqttClient client, Function<VitalStat,
            byte[]> converter,@Value("${vitals.mqtt.topic}") String topic)
    {
        this.client = client;
        this.converter = converter;
        this.topic = topic;
    }

    /**
     * Send the vital stats using MQTT
     *
     * @param vitalStat the vitals to send
     */
    @SneakyThrows
    @Override
    public void accept(VitalStat vitalStat)
    {
        log.info("Sending {}", vitalStat);
        var payload = converter.apply(vitalStat);
        var message = new MqttMessage(payload);
        client.publish(topic,message);
    }
}
