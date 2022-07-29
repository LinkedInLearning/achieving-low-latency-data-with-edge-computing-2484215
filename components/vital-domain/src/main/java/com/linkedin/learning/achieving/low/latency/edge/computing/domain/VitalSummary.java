package com.linkedin.learning.achieving.low.latency.edge.computing.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VitalSummary
 *
 * @author Gregory Green
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VitalSummary
{
    private Vital vital;
    private VitalOverview vitalOverview;
}
