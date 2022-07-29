package com.linkedin.learning.achieving.low.latency.edge.computing.services.alarms;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.alerts.repositories.VitalAlertRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalOverview;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalSummary;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.aggregates.ConditionSummaries;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalOverviewRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * VitalOverviewDataService
 *
 * @author Gregory Green
 */
@Component
@Setter
@NoArgsConstructor
public class AlarmDataService implements ApplicationContextAware
{
    private VitalRepository vitalRepository;
    private VitalOverviewRepository vitalOverviewRepository;
    private  VitalAlertRepository vitalAlertRepository;

    @Value("${vital.alarms.goodCountThreshold:2}")
    private final int goodCountThreshold = 2;

    @Value("${vital.alarms.warningLow:3}")
    private final int warningLow = goodCountThreshold+1;

    @Value("${vital.alarms.warningHigh:6}")
    private final int warningHigh = warningLow+3;


    private final static short lowPriority = 0;
    private final static short warningPriority = 1;
    private final static short severePriority = 2;


    private ApplicationContext applicationContext;


    public VitalSummary processAlarm(VitalStat vitalStat)
    {
        init();

        // get vital overview
        VitalOverview vitalOverview = this.vitalOverviewRepository
                .findById(vitalStat.getVitalId()).orElse(
                        new VitalOverview());

        vitalOverview.setId(vitalStat.getVitalId());

        //Get Vital
        var vital = this.vitalRepository.findById(vitalStat.getVitalId()).orElse(null);

        //overview.alarm++
        long alarmCount = vitalOverview.getAlarmCount();
        alarmCount++;
        vitalOverview.setAlarmCount(alarmCount);


        if(alarmCount < warningLow)
            vitalOverview.setPriority(lowPriority);
        else if(alarmCount >= warningLow && alarmCount <= warningHigh)
            vitalOverview.setPriority(warningPriority);
        else
        {
            vitalOverview.setPriority(severePriority);
        }

        vitalOverviewRepository.save(vitalOverview);

        return VitalSummary.builder()
                           .vitalOverview(vitalOverview)
                           .vital(vital).build();
    }


    public ConditionSummaries calculateConditionCounts()
    {
        init();

        return ConditionSummaries.builder()
                                 .lowCount(vitalOverviewRepository.countByPriority(lowPriority))
                                 .warningCount(vitalOverviewRepository.countByPriority(warningPriority))
                                 .severeCount(vitalOverviewRepository.countByPriority(severePriority))
                                 .build();

    }

    /**
     *
     * @param vitalAlert vital alert
     * @return the alert details
     */
    public VitalAlert processAndSaveAlarm(VitalAlert vitalAlert)
    {
        var summary = processAlarm(vitalAlert.getVitalStat());

        vitalAlert.setVitalSummary(summary);

        vitalAlertRepository.save(vitalAlert);

        return vitalAlert;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {

        this.applicationContext = applicationContext;
    }

    private void init()
    {
        if(this.vitalRepository == null)
            this.vitalRepository = applicationContext.getBean("vitalRepository",VitalRepository.class);

        if(this.vitalOverviewRepository == null)
            this.vitalOverviewRepository = applicationContext.getBean("vitalOverviewRepository",VitalOverviewRepository.class);


        if(this.vitalAlertRepository == null)
            this.vitalAlertRepository = applicationContext.getBean("vitalAlertRepository",VitalAlertRepository.class);
    }

    public void saveVitalAlert(VitalAlert vitalAlert)
    {
        init();

        vitalAlertRepository.save(vitalAlert);
    }
}
