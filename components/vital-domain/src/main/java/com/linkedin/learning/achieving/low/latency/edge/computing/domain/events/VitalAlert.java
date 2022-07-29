package com.linkedin.learning.achieving.low.latency.edge.computing.domain.events;

import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VitalAlert
 *
 * @author Gregory Green
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VitalAlert
{
    private String id;
    private VitalStat vitalStat;
    private VitalSummary vitalSummary;
}
