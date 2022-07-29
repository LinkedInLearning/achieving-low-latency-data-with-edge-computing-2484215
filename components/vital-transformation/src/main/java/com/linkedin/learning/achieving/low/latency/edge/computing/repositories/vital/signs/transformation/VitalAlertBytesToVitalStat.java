package com.linkedin.learning.achieving.low.latency.edge.computing.repositories.vital.signs.transformation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.VitalStat;
import com.linkedin.learning.achieving.low.latency.edge.computing.domain.events.VitalAlert;
import lombok.SneakyThrows;

import java.util.function.Function;

/**
 * VitalAlertBytesToVitalStat
 *
 * @author Gregory Green
 */
public class VitalAlertBytesToVitalStat implements Function<byte[], VitalStat>
{
    private final ObjectMapper objectMapper;

    public VitalAlertBytesToVitalStat()
    {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    /**
     * Applies this function to the given argument.
     *
     * @param bytes the function argument
     * @return the function result
     */
    @SneakyThrows
    @Override
    public VitalStat apply(byte[] bytes)
    {
        return this.objectMapper.readValue(bytes, VitalAlert.class).getVitalStat();
    }
}
