package org.raven.serializer.mybatis.type;


import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;

public class ObjectMapperWrapper {
    private ObjectMapper objectMapper;

    public ObjectMapperWrapper() {
        this.objectMapper = new ObjectMapper();
    }

    public ObjectMapperWrapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T fromString(String string, Class<T> clazz) {
        try {
            return objectMapper.readValue(string, clazz);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("The given string value: " + string + " cannot be transformed to Json object", e);
        }
    }

    public <T> T fromString(String string, Type type) {
        try {
            return objectMapper.readValue(string, objectMapper.getTypeFactory().constructType(type));
        } catch (JacksonException e) {
            throw new IllegalArgumentException("The given string value: " + string + " cannot be transformed to Json object", e);
        }
    }

    public <T> T fromBytes(byte[] value, Class<T> clazz) {
        try {
            return objectMapper.readValue(value, clazz);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("The given byte array cannot be transformed to Json object", e);
        }
    }

    public <T> T fromBytes(byte[] value, Type type) {
        try {
            return objectMapper.readValue(value, objectMapper.getTypeFactory().constructType(type));
        } catch (JacksonException e) {
            throw new IllegalArgumentException("The given byte array cannot be transformed to Json object", e);
        }
    }

    public String toString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a String", e);
        }
    }

    public byte[] toBytes(Object value) {
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("The given Json object value: " + value + " cannot be transformed to a byte array", e);
        }
    }
}
