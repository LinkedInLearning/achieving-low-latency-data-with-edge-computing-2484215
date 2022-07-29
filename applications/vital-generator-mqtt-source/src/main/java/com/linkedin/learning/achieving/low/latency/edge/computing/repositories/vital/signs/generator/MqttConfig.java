package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.generator;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.transformation.VitalStatsToBytes;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
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
@ComponentScan(basePackageClasses = {VitalStatsToBytes.class})
public class MqttConfig
{
    @Value("${spring.application.name}")
    private String clientId = "generator-mqtt-source";

    @Value("${mqtt.connectionUrl:tcp://localhost:1883}")
    private String connectionUrl = "tcp://localhost:1883";

    @Value("${mqtt.userName:mqtt}")
    private String userName = "mqtt";

    @Value("${mqtt.userPassword:mqtt}")
    private String userPassword = "mqtt";

    @Bean
    IMqttClient mqttClientPublisher() throws MqttException
    {
        var mqttClient = new MqttClient(connectionUrl, clientId, new MemoryPersistence());

        var options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
//        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setUserName(userName);
        options.setPassword(userPassword.toCharArray());
        mqttClient.connect(options);

        return mqttClient;

    }
}
