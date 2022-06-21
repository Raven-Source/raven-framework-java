package org.raven.spring.web.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yanfeng
 * date 2021.08.12
 */
public abstract class RequestContextUtil {

    /**
     * 注意跨线程时，获取不到会然后null
     *
     * @return javax.servlet.http.HttpServletRequest
     */
    public static HttpServletRequest getRequest() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletWebRequest) {
            return ((ServletWebRequest) requestAttributes).getRequest();
        }
        return null;

    }

    /**
     * 注意跨线程时，获取不到会然后null
     *
     * @return javax.servlet.http.HttpServletResponse
     */
    public static HttpServletResponse getResponse() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletWebRequest) {
            return ((ServletWebRequest) requestAttributes).getResponse();
        }
        return null;

    }

}
