package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.alerts.repositories;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * VitalAlertRepository
 *
 * @author Gregory Green
 */
@Repository
public interface VitalAlertRepository extends CrudRepository<VitalAlert,String>
{
}
