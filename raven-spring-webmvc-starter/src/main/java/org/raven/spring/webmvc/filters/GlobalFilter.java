package org.raven.spring.webmvc.filters;

import org.apache.commons.lang3.StringUtils;
import org.raven.commons.constant.MetaDataConstant;
import org.raven.commons.context.ContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author by yanfeng
 * date 2021/10/3 22:43
 */
@Component
public class GlobalFilter extends AbstractFilter {

    @Value("${interlyst.filter.global.order:0}")
    private static int ORDER;

    @Value("${interlyst.filter.global.path:/*}")
    private static String PATH;

    @Autowired
    private ContextHolder contextHolder;

    public GlobalFilter() {
        super(ORDER, PATH);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        boolean isTest = false;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String isTestStr = httpRequest.getHeader(MetaDataConstant.TEST);
            if (StringUtils.isBlank(isTestStr)) {
                isTestStr = httpRequest.getParameter(MetaDataConstant.TEST);
            }

            if (StringUtils.isNotBlank(isTestStr)) {
                isTest = Boolean.parseBoolean(isTestStr);
            }
        }
        contextHolder.getContext().setAttribute(MetaDataConstant.TEST, isTest);

        try {
            chain.doFilter(request, response);
        } finally {
            contextHolder.clearContext();
        }


    }

}
