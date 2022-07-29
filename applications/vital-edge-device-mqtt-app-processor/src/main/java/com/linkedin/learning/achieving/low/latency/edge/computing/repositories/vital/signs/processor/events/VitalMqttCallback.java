package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * VitalMqttCallback
 *
 * @author Gregory Green
 */
@Component
public class VitalMqttCallback implements MqttCallback
{
    @Override
    public void connectionLost(Throwable throwable)
    {
        throwable.printStackTrace();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception
    {
        System.out.println(s+" msg:"+mqttMessage);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
    {
        System.out.println(iMqttDeliveryToken);
    }
}
