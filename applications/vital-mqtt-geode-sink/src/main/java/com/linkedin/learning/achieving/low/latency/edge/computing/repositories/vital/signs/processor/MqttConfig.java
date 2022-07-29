package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor;

import com.linkedin.learning.achieving.low.latency.edge.computing.transformation.BytesToJson;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import com.linkedin.learning.achieving.low.latency.edge.computing.generator.mqtt.consumer.json.JsonMqttSenderConsumer;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.VitalMqttCallback;
import org.apache.geode.cache.GemFireCache;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * MqttConfig
 *
 * @author Gregory Green
 */
@Configuration
@ComponentScan(basePackageClasses = {JsonMqttSenderConsumer.class})
public class MqttConfig
{
    @Value("${spring.application.name:vital-mqtt-geode-source}")
    private String clientId = "vital-mqtt-geode-source";

    @Value("${mqtt.connectionUrl:tcp://localhost:1883}")
    private String connectionUrl = "tcp://localhost:1883";

    @Value("${mqtt.userName:guest}")
    private String userName = "mqtt";

    @Value("${mqtt.userPassword:guest}")
    private String userPassword = "mqtt";

    @Bean("bytesToJsonVitalAlert")
    public BytesToJson<VitalAlert> bytesToJsonVitalStats()
    {
        return new BytesToJson<VitalAlert>(VitalAlert.class);
    }

    @Bean
    IMqttClient mqttClient(GemFireCache cache,  VitalMqttCallback vitalCallBack) throws MqttException
    {
        var mqttClient = new MqttClient(connectionUrl, clientId, new MemoryPersistence());

        var options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setConnectionTimeout(10);
        options.setUserName(userName);
        options.setPassword(userPassword.toCharArray());
        mqttClient.connect(options);
        mqttClient.setCallback(vitalCallBack);
        return mqttClient;
    }

}
