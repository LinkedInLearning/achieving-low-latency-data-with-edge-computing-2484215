package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.stats.repository;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.Vital;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * VitalRepository for Vital domain
 *
 * @author Gregory Green
 */
@Lazy
@Repository
public interface VitalRepository extends CrudRepository<Vital,String>
{
}
