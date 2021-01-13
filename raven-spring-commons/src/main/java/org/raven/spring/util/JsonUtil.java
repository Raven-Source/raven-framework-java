package org.raven.spring.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.05.28 14:37
 */
@Slf4j
@Component
public class JsonUtil {
    private static ObjectMapper mapper;

    private JsonUtil() {
    }

    @Autowired
    private void setMapper(ObjectMapper mapper) {
        JsonUtil.mapper = mapper;
    }

    /**
     * @param content
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String content, Class<T> target) {

        try {
            return mapper.readValue(content, target);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * @param content
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String content, TypeReference<T> target) {

        try {
            return mapper.readValue(content, target);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param content
     * @param parametrized
     * @param parameterClasses
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String content, Class<?> parametrized, Class<?>... parameterClasses) {

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
            return mapper.readValue(content, javaType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param content
     * @param target
     * @param <T>
     * @return
     */
    public static <T> List<T> parseList(String content, Class<T> target) {

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, target);
            return mapper.readValue(content, javaType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * @param value
     * @return
     */
    public static String toJsonString(Object value) {

        try {
            return mapper.writeValueAsString(value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }

    }

    /**
     * @param fromValue
     * @param toValueType
     * @param <T>
     * @return
     */
    public static <T> T convert(Object fromValue, Class<T> toValueType) {

        try {
            return mapper.convertValue(fromValue, toValueType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param fromValue
     * @param toValueType
     * @param <T>
     * @return
     */
    public static <T> T convert(Object fromValue, TypeReference<T> toValueType) {

        try {
            return mapper.convertValue(fromValue, toValueType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param source
     * @param <T>
     * @return
     */
    public static <T> T copy(T source, Class<T> target) {

        try {
            byte[] data = mapper.writeValueAsBytes(source);
            return mapper.readValue(data, target);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param source
     * @param <T>
     * @return
     */
    public static <T> T copy(T source, TypeReference<T> target) {

        try {
            byte[] data = mapper.writeValueAsBytes(source);
            return mapper.readValue(data, target);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * @param source
     * @param <T>
     * @return
     */
    public static <T> T copy(T source, Class<?> parametrized, Class<?>... parameterClasses) {

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
            byte[] data = mapper.writeValueAsBytes(source);
            return mapper.readValue(data, javaType);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
