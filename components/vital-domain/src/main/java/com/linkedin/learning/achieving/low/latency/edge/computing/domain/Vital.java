package com.linkedin.learning.achieving.low.latency.edge.computing.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vital
{
    private String id;
    private String name;
    private String url;
    private VitalStatName[] stats;
    private String location;

}
