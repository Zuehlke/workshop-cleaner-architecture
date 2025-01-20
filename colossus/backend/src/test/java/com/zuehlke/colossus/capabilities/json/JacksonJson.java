package com.zuehlke.colossus.capabilities.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the {@link Json} interface using Jackson technology.
 *
 */
public class JacksonJson {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JacksonJson create() {return new JacksonJson();}

    public <T> T parse(String jsonInput, Class<? extends T> targetType) throws Exception {
        try {
            return objectMapper.readValue(jsonInput, targetType);
        } catch (Exception bad) {
            throw bad;
        }
    }

}
