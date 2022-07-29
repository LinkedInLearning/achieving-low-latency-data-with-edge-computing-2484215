package com.linkedin.learning.achieving.low.latency.edge.computing.mqtt.consumer;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalStatRepository;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.function.Function;

/**
 * VitalStatMqttConsumer
 *
 * @author Gregory Green
 */
//@Component
public class VitalStatMqttGeodeListener implements IMqttMessageListener
{
    private final VitalStatRepository vitalStatRepository;
    private final Function<byte[], VitalStat> toVitalStat;

    public VitalStatMqttGeodeListener(VitalStatRepository vitalStatRepository, Function<byte[], VitalStat> toVitalStat)
    {
        this.vitalStatRepository = vitalStatRepository;
        this.toVitalStat = toVitalStat;
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception
    {
        var vitalStat = toVitalStat.apply(mqttMessage.getPayload());

        this.vitalStatRepository.save(vitalStat);

    }
}
