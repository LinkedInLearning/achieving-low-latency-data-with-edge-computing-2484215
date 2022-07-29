package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.processor;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.transformation.VitalStatsToBytes;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStatName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * LifeScienceConfig
 *
 * @author Gregory Green
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackageClasses = {VitalStatsToBytes.class})
public class VitalStatsConfig
{

    @Bean
    public VitalStatName[] lifeScienceVitalStatNames(){
        VitalStatName[] stats = {
                new VitalStatName("heartRate"," Heart Rate",0,135),
                new VitalStatName("bodyTemperature","Body temperature",75,125),
                new VitalStatName("respirationRate","Respiration rate",0,30),
                new VitalStatName("bloodPressureSystolic","Blood pressure systolic",90,160),
                new VitalStatName("bloodPressureDiastolic","Blood pressure diastolic",40,120)
        };

        return stats;
    }
}
