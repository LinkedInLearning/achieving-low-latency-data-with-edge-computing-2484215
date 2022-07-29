package com.linkedin.learning.achieving.low.latency.edge.computing.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VitalOverview
 *
 * @author Gregory Green
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VitalOverview
{
    private String id;
    private short priority;
    private long alarmCount;
}