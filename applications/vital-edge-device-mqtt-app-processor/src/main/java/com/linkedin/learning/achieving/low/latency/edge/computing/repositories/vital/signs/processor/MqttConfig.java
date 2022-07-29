package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.transformation.VitalStatsToBytes;
import com.linkedin.learning.achieving.low.latency.edge.computing.transformation.BytesToJson;
import com.linkedin.learning.achieving.low.latency.edge.computing.mqtt.consumer.VitalStatMqttGeodeListener;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.generator.mqtt.consumer.json.JsonMqttSenderConsumer;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.VitalMqttCallback;
import org.apache.geode.cache.GemFireCache;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * MqttConfig
 *
 * @author Gregory Green
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackageClasses = {VitalStatsToBytes.class, JsonMqttSenderConsumer.class,VitalStatMqttGeodeListener.class})
public class MqttConfig
{
    @Value("${spring.application.name}")
    private String consumerClientId = "vital-edge-device-mqtt-app-processor-in";
    private String publisherClientId = "vital-edge-device-mqtt-app-processor-out";

    @Value("${mqtt.connectionUrl:tcp://localhost:1883}")
    private String connectionUrl = "tcp://localhost:1883";

    @Value("${mqtt.userName:guest}")
    private String userName = "mqtt";

    @Value("${mqtt.userPassword:guest}")
    private String userPassword = "mqtt";

    @Value("${mqtt.edge.vital.stats.topic:vitals}")
    private String subscriberTopics = "vitals";


    @Bean("bytesToJsonVitalStats")
    public BytesToJson<VitalStat> bytesToJsonVitalStats()
    {
        return new BytesToJson<VitalStat>(VitalStat.class);
    }

    @Bean("consumersConnection")
    IMqttClient consumersConnection(GemFireCache cache,  VitalMqttCallback vitalCallBack) throws MqttException
    {
        var mqttClient = new MqttClient(connectionUrl, consumerClientId, new MemoryPersistence());

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

    @Bean("publisherConnection")
    IMqttClient publisherConnection(GemFireCache cache) throws MqttException
    {
        var mqttClient = new MqttClient(connectionUrl, publisherClientId, new MemoryPersistence());

        var options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(false);
        options.setConnectionTimeout(10);
        options.setUserName(userName);
        options.setPassword(userPassword.toCharArray());
        mqttClient.connect(options);
        return mqttClient;
    }

}
