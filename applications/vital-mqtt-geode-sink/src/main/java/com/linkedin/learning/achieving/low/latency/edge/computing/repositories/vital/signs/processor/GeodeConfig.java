package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.alerts.repositories.VitalAlertRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalStatRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.Vital;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalOverview;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import com.linkedin.learning.achieving.low.latency.edge.computing.services.vital.VitalDataService;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableSecurity;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@Configuration
@ClientCacheApplication
@EnableSecurity
@EnableGemfireRepositories(basePackageClasses = {
        VitalRepository.class,
        VitalStatRepository.class,
        VitalAlertRepository.class})
@ComponentScan(basePackageClasses = {VitalDataService.class})
public class GeodeConfig
{
    @Bean
    public ClientRegionFactoryBean<String, VitalAlert> vitalAlert(GemFireCache gemFireCache)
    {

        var region = new ClientRegionFactoryBean<String, VitalAlert>();
        region.setCache(gemFireCache);
        region.setName("VitalAlert");
        region.setDataPolicy(DataPolicy.EMPTY);
        return region;
    }

    @Bean
    public ClientRegionFactoryBean<String, Vital> vital(GemFireCache gemFireCache)
    {

        var region = new ClientRegionFactoryBean<String, Vital>();
        region.setCache(gemFireCache);
        region.setName("Vital");
        region.setDataPolicy(DataPolicy.EMPTY);
        return region;
    }

    @Bean
    public ClientRegionFactoryBean<String, VitalStat> vitalStat(GemFireCache gemFireCache)
    {

        var region = new ClientRegionFactoryBean<String, VitalStat>();
        region.setCache(gemFireCache);
        region.setName("VitalStat");
        region.setDataPolicy(DataPolicy.EMPTY);
        return region;
    }

    @Bean
    public ClientRegionFactoryBean<String, VitalOverview> vitalOverview(GemFireCache gemFireCache)
    {

        var region = new ClientRegionFactoryBean<String, VitalOverview>();
        region.setCache(gemFireCache);
        region.setName("VitalOverview");
        region.setDataPolicy(DataPolicy.EMPTY);
        return region;
    }
}
