package com.linkedin.learning.achieving.low.latency.edge.computing.generator.mqtt.consumer.json;

import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * JsonMqttSenderConsumer
 *
 * @author Gregory Green
 */
public class JsonMqttSenderConsumer<T> implements Consumer<T>
{
    private final IMqttClient client;
    private final Function<T,byte[]> converter;
    private final String topic;
    private final Logger log = LoggerFactory.getLogger(JsonMqttSenderConsumer.class);

    public JsonMqttSenderConsumer(@Qualifier("consumersConnection") IMqttClient client, Function<T,
            byte[]> converter, @Value("${vitals.mqtt.topic}") String topic)
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
    public void accept(T vitalStat)
    {
        log.info("Sending {}", vitalStat);
        var payload = converter.apply(vitalStat);
        var message = new MqttMessage(payload);
        client.publish(topic,message);
    }
}
