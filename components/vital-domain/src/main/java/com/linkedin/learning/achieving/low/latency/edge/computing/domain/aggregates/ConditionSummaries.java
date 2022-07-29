package com.linkedin.learning.achieving.low.latency.edge.computing.domain.aggregates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ConditionSummaries
 *
 * @author Gregory Green
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionSummaries
{
    private int lowCount;
    private int warningCount;
    private int severeCount;
}
