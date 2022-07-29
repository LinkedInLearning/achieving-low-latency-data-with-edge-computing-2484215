package com.linkedin.learning.achieving.low.latency.edge.computing.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VitalStat
{
    private String id;
    private String vitalId;
    private String statName;
    private double value;
    private String label;
    private LocalDateTime timestamp;
}
