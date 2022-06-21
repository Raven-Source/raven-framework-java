package org.raven.commons.contract;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.annotation.Contract;
import org.raven.commons.data.annotation.Ignore;
import org.raven.commons.data.annotation.Member;
import org.raven.commons.property.PropertyDefiner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
@Slf4j
@Data
@Contract
@SuppressWarnings("unchecked")
public class ResponseModel<TData> implements Response<TData, String, Extension> {

    @Member
    private boolean success;

    @Member
    private String code;

    @Member
    private TData data;

    @Member
    private String message;

    @Member
    private Extension extension;

    public ResponseModel() {
        this.extension = new Extension();
    }

    public ResponseModel(TData data, String message, String code) {
        this();
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public ResponseModel(TData data, String message, String code, boolean success) {
        this(data, message, code);
        this.success = success;
    }

    @Ignore
    public void extend(String key, Object val) {
        this.extension.put(key, val);
    }

    static {
        try {
            for (Field field : ResponseModel.class.getDeclaredFields()) {
                Annotation annotation = field.getAnnotation(Member.class);
                if (annotation != null) {
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
                    Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
                    memberValues.setAccessible(true);
                    if (memberValues.get(invocationHandler) instanceof Map) {
                        Map<String, Object> memberValuesMap = (Map<String, Object>) (memberValues.get(invocationHandler));
                        memberValuesMap.put("value", PropertyDefiner.PROPERTIES.get("contract.responseModel." + field.getName()));
                    }
                }

            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
