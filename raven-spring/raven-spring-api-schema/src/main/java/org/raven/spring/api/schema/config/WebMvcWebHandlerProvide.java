package org.raven.spring.api.schema.config;

import org.raven.spring.api.schema.spi.WebHandlerDescribable;
import org.raven.spring.api.schema.spi.WebHandlerProvide;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WebMvcWebHandlerProvide implements WebHandlerProvide {

    private final Map<Method, RequestMappingInfo> handlerMethodMap = new HashMap<>();

    public WebMvcWebHandlerProvide(RequestMappingHandlerMapping handlerMapping) {

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMapping.getHandlerMethods().entrySet()) {

            RequestMappingInfo info = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();

            handlerMethodMap.put(handlerMethod.getMethod(), info);
        }
    }

    @Override
    public WebHandlerDescribable getWebHandlerDeclaration(Class<?> clazz, Method method) {

        RequestMappingInfo requestMappingInfo = handlerMethodMap.get(method);

        if (requestMappingInfo != null) {

            WebHandlerDescribable webHandlerInfo = new WebHandlerDescribable();
            webHandlerInfo.setPaths(requestMappingInfo.getDirectPaths());
            webHandlerInfo.setMethods(
                    requestMappingInfo.getMethodsCondition().getMethods()
                            .stream().map(Enum::name).collect(Collectors.toSet())
            );

            return webHandlerInfo;
        }

        return null;
    }
}
