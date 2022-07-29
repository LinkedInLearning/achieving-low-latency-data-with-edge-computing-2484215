package com.linkedin.learning.achieving.low.latency.edge.computing.mqtt.consumer;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.function.Function;

/**
 * VitalStatMqttConsumer
 *
 * @author Gregory Green
 */
public class MqttCrudRepositoryListener<T,ID> implements IMqttMessageListener
{
    private final CrudRepository<T,ID> repository;
    private final Function<byte[], T> converter;

    public MqttCrudRepositoryListener(CrudRepository<T,ID> repository, Function<byte[], T> converter)
    {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception
    {
        var vitalStat = converter.apply(mqttMessage.getPayload());

        this.repository.save(vitalStat);

    }
}
