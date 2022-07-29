package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.repositories;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalOverview;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * VitalOverviewRepository data access interview.
 * Include CRUD and basic counts
 *
 * @author Gregory Green
 */
@Repository
public interface VitalOverviewRepository extends CrudRepository<VitalOverview,String>
{

    @Query("SELECT count(*) FROM /VitalOverview x WHERE x.alarmCount < $1")
    int countByAlarmCountLessThan(Integer alarmCount);

    @Query("SELECT count(*) FROM /VitalOverview x WHERE x.alarmCount > $1")
    int countByAlarmCountGreaterThan(Integer alarmCount);

    @Query("SELECT count(*) FROM /VitalOverview x WHERE x.alarmCount < $1 and x.alarmCount > $1")
    int countByAlarmCountBetween(Integer lowAlarmCount,Integer highAlarmCount);

    @Query("SELECT count(*) FROM /VitalOverview x WHERE x.priority = $1 ")
    int countByPriority(Short priority);
}
