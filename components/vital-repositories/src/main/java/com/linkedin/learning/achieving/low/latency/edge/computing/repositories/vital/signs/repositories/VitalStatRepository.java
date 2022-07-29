package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * VitalStatRepository
 *
 * @author Gregory Green
 */
@Repository
public interface VitalStatRepository extends CrudRepository<VitalStat,String>
{
}
