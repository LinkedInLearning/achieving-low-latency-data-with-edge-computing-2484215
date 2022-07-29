package com.linkedin.learning.achieving.low.latency.edge.computing.repository.consumer.stats;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories.VitalStatRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * VitalStatsRepositoryConsumer using the given repository to save the vital stat
 *
 * @author Gregory Green
 */
@Component
@Primary
public class VitalStatsRepositoryConsumer implements Consumer<VitalStat>
{
    private final VitalStatRepository repository;

    public VitalStatsRepositoryConsumer(VitalStatRepository repository)
    {
        this.repository = repository;
    }

    /**
     * Save the vital stat
     *
     * @param vitalStat the vital stat to process
     */
    @Override
    public void accept(VitalStat vitalStat)
    {
        repository.save(vitalStat);
    }
}
