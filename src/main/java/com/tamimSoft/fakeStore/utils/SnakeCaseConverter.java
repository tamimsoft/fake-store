package com.tamimSoft.fakeStore.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class SnakeCaseConverter implements Converter<Object, Object> {

    private final ObjectMapper objectMapper;

    public SnakeCaseConverter() {
        // Jackson ObjectMapper with SnakeCase strategy
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    @Override
    public Object convert(MappingContext<Object, Object> context) {
        try {
            // Convert the source object to JSON, apply SnakeCase, and return the target
            String json = objectMapper.writeValueAsString(context.getSource());
            return objectMapper.readValue(json, context.getDestinationType());
        } catch (Exception e) {
            throw new RuntimeException("Error during SnakeCase conversion", e);
        }
    }
}

