package com.linkedin.learning.achieving.low.latency.edge.computing.vital.sign.alerts;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableContinuousQueries;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;

@Configuration
@ClientCacheApplication(subscriptionEnabled = true)
@EnableSecurity
@EnableContinuousQueries
public class GeodeConfig
{
    @Value("${spring.data.gemfire.pool.locators}")
    private String locators;

    @Bean
    public ClientRegionFactoryBean<String, VitalStat> vitalStat(
            GemFireCache gemFireCache)
    {

        var region = new ClientRegionFactoryBean<String, VitalStat>();
        region.setCache(gemFireCache);
        region.setName("VitalAlert");
        region.setDataPolicy(DataPolicy.EMPTY);
        return region;
    }

}
