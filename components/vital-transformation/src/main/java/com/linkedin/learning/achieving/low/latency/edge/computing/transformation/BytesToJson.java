package com.linkedin.learning.achieving.low.latency.edge.computing.transformation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

import java.util.function.Function;

/**
 * BytesToJson
 *
 * @author Gregory Green
 */
public class BytesToJson <T> implements Function<byte[],T>
{
    private final ObjectMapper objectMapper;
    private final Class<T> typeClass;

    public BytesToJson(Class<T> typeClass)
    {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        this.typeClass = typeClass;
    }
    /**
     * Applies this function to the given argument.
     *
     * @param bytes the function argument
     * @return the function result
     */
    @SneakyThrows
    @Override
    public T apply(byte[] bytes)
    {
        return (T)objectMapper.readValue(bytes,typeClass);
    }
}
