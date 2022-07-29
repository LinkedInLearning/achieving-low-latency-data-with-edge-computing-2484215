package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.transformation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * VitalStatsToBytes
 *
 * @author Gregory Green
 */
@Component
public class VitalStatsToBytes implements Function<VitalStat,byte[]>
{
    private final ObjectMapper objectMapper;

    public VitalStatsToBytes()
    {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }


    @SneakyThrows
    @Override
    public byte[] apply(VitalStat vitalStat)
    {
        return objectMapper.writeValueAsBytes(vitalStat);
    }
}
