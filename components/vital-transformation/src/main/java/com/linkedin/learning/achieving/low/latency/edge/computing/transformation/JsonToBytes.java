package com.linkedin.learning.achieving.low.latency.edge.computing.transformation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * JsonToBytes
 *
 * @author Gregory Green
 */
@Component
public class JsonToBytes<T> implements Function<T,byte[]>
{
    private final ObjectMapper objectMapper;

    public JsonToBytes()
    {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }


    @SneakyThrows
    @Override
    public byte[] apply(T vitalStat)
    {
        return objectMapper.writeValueAsBytes(vitalStat);
    }
}
