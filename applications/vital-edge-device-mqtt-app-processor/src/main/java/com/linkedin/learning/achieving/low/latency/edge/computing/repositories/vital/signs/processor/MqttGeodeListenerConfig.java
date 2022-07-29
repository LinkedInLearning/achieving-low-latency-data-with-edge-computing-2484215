package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.alerts.repositories.VitalAlertRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalStatRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.transformation.BytesToJson;
import com.linkedin.learning.achieving.low.latency.edge.computing.mqtt.consumer.VitalStatMqttGeodeListener;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.alarms.AlarmDataService;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

/**
 * MqttGeodeListenerConfig
 *
 * @author Gregory Green
 */
@Configuration
@ComponentScan(basePackageClasses = {AlarmDataService.class})
@EnableGemfireRepositories(basePackageClasses = {VitalRepository.class, VitalStatRepository.class, VitalAlertRepository.class})
public class MqttGeodeListenerConfig
{
    private String queueName = "vitals";

    @Bean
    public VitalStatMqttGeodeListener vitalStatMqttGeodeListener(VitalStatRepository repository,
                                                                 @Qualifier("consumersConnection") IMqttClient mqttClient)
    throws MqttException
    {
        var vitalStatMqttGeodeListener = new VitalStatMqttGeodeListener(repository,new BytesToJson<>(VitalStat.class));
        mqttClient.subscribe(queueName,vitalStatMqttGeodeListener);
        return vitalStatMqttGeodeListener;
    }

}
