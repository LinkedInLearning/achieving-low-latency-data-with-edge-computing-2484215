package com.linkedin.learning.achieving.low.latency.edge.computing.generator.stats.geode;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalOverview;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalOverviewRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * VitalOverviewGeodeGenerator
 *
 * @author Gregory Green
 */
@Component
public class VitalOverviewGeodeGenerator
{

    private final VitalOverviewRepository repository;
    private final int minVitalId;
    private final int maxVitalId;

    public VitalOverviewGeodeGenerator(VitalOverviewRepository repository, @Value("${vitals.minVitalId}")
            int minVitalId,
                                       @Value("${vitals.maxVitalId}")
                                               int maxVitalId)
    {
        this.repository = repository;
        this.minVitalId = minVitalId;
        this.maxVitalId = maxVitalId;
    }


    public List<VitalOverview> generateOverviews(int count)
    {
        final short zero =0;
        VitalOverview vitalOverview;

        var list = new ArrayList<VitalOverview>(count);
        var id = minVitalId;
        for (int i = 0; i < count; i++) {

            vitalOverview = VitalOverview.builder()
                        .id(String.valueOf(id))
                        .alarmCount(0)
                        .priority(zero).build();

            id = (id +1)%(maxVitalId+1);
            if(id == 0)
                id = minVitalId;

            repository.save(vitalOverview);

            list.add(vitalOverview);
        }

        return list;
    }
}
