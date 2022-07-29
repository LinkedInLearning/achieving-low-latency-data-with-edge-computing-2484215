package com.linkedin.learning.achieving.low.latency.edge.computing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VitalStatName
{
    private String statName;
    private String label;
    private double min;
    private double max;
}
