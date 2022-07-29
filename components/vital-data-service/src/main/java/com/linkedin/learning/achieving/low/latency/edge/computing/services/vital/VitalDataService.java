package com.linkedin.learning.achieving.low.latency.edge.computing.services.vital;

import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.alerts.repositories.VitalAlertRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.Vital;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalOverview;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalOverviewRepository;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository.VitalRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * VitalDataService access to vital statistics information
 *
 * @author Gregory Green
 */
@Service
@Component
public class VitalDataService

{

    private final VitalRepository vitalRepository;
    private final VitalOverviewRepository vitalOverviewRepository;
    private final VitalAlertRepository vitalAlertRepository;



    public VitalDataService(VitalRepository vitalRepository, VitalOverviewRepository vitalOverviewRepository,
                            VitalAlertRepository vitalAlertRepository)
    {
        this.vitalRepository = vitalRepository;
        this.vitalOverviewRepository = vitalOverviewRepository;
        this.vitalAlertRepository = vitalAlertRepository;

    }


    public Optional<VitalOverview> findVitalOverviewById(String vitalId)
    {
        return vitalOverviewRepository.findById(vitalId);
    }

    public Optional<Vital> findVitalById(String vitalId)
    {
        return vitalRepository.findById(vitalId);
    }




    /**
     *
     * @return the good, warning and serious counts
     */


    public Iterable<VitalOverview> findVitalOverviews()
    {
        return this.vitalOverviewRepository.findAll();
    }


}
