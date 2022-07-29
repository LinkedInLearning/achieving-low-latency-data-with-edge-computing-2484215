package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.alerts.repositories.VitalAlertRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.VitalAlertAsyncEventListener;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor.events.stomp.VitalStatConsumerStompPublisher;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalOverviewRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.rules.VitalRuleService;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.transformation.JsonToBytes;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.Vital;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalOverview;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import com.linkedin.learning.achieving.low.latency.edge.computing.generator.mqtt.consumer.json.JsonMqttSenderConsumer;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.alarms.AlarmDataService;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.vital.VitalDataService;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.asyncqueue.AsyncEventQueue;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.data.gemfire.config.annotation.EnableExpiration;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.expiration.ExpirationActionType;
import org.springframework.data.gemfire.expiration.ExpirationAttributesFactoryBean;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
@EnablePdx(persistent = true)
@CacheServerApplication(port = 50505, bindAddress = "127.0.0.1", hostnameForClients = "127.0.0.1")
@EnableExpiration(policies = {
        @EnableExpiration.ExpirationPolicy(
                regionNames =  {"VitalStat","Vital","VitalOverview","VitalAlert"},
                types = EnableExpiration.ExpirationType.TIME_TO_LIVE,
                action = ExpirationActionType.DESTROY,
                timeout = 30000) //time in seconds
})
@ComponentScan(basePackageClasses = {VitalRepository.class})
//@EnableGemfireRepositories(basePackageClasses = {VitalStatRepository.class, VitalAlertRepository.class,VitalRepository.class})
@ComponentScan(basePackageClasses = {VitalDataService.class, VitalRuleService.class})
public class GemFireConfig
{
    private final DataPolicy dataPolicy = DataPolicy.PARTITION;

    @Value("${vital.alert.queue:vitalAlerts}")
    private String alertQueueName;

    @Bean
    public PartitionedRegionFactoryBean<String, VitalStat> vitalStat(
            GemFireCache gemFireCache,
            @Qualifier("publisherConnection") IMqttClient publisherMqttClient,
            VitalRuleService vitalRuleService,
            VitalStatConsumerStompPublisher vitalStatConsumer,
            AlarmDataService alarmDataService) throws MqttException
    {

        Function<VitalAlert, byte[]> transformer = new JsonToBytes<>();
        Consumer< VitalAlert > vitalAlertSenderConsumer= new JsonMqttSenderConsumer<VitalAlert>(publisherMqttClient,
                transformer, alertQueueName);


        VitalRepository vitalRepository = null;
        VitalOverviewRepository vitalOverviewRepository = null;
        VitalAlertRepository vitalAlertRepository = null;

        VitalAlertAsyncEventListener listener =  new VitalAlertAsyncEventListener(vitalAlertSenderConsumer,vitalRuleService, vitalStatConsumer, alarmDataService);


        var region = new PartitionedRegionFactoryBean<String, VitalStat>();
        region.setCache(gemFireCache);
        region.setName("VitalStat");
        region.setDataPolicy(dataPolicy);

        var expirationFactory = new ExpirationAttributesFactoryBean();
        expirationFactory.setTimeout(1000);
        AsyncEventQueue asyncQueue =  CacheFactory.getAnyInstance()
                                                  .createAsyncEventQueueFactory()
                                                  .create(alertQueueName,listener);
        AsyncEventQueue[] asyncQueues = {asyncQueue};
        region.addAsyncEventQueues(asyncQueues);


        return region;
    }


    @Bean
    public PartitionedRegionFactoryBean<String, Vital> vitalRegion(
            GemFireCache gemFireCache)
    {
        var region = new PartitionedRegionFactoryBean<String, Vital>();
        region.setCache(gemFireCache);
        region.setName("Vital");
        region.setDataPolicy(dataPolicy);
        return region;
    }

    @Bean
    public PartitionedRegionFactoryBean<String, VitalOverview> vitalOverviewRegion(
            GemFireCache gemFireCache)
    {
        var region = new PartitionedRegionFactoryBean<String, VitalOverview>();
        region.setCache(gemFireCache);
        region.setName("VitalOverview");
        region.setDataPolicy(dataPolicy);
        return region;
    }

    @Bean
    public PartitionedRegionFactoryBean<String, VitalAlert> vitalAlertRegion(
            GemFireCache gemFireCache)
    {
        var region = new PartitionedRegionFactoryBean<String, VitalAlert>();
        region.setCache(gemFireCache);
        region.setName("VitalAlert");
        region.setDataPolicy(dataPolicy);
        return region;
    }

}
