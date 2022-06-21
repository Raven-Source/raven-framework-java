package org.raven.spring.webmvc.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yanfeng
 * date 2019.03.28 16:53
 */
@Component
@ConditionalOnProperty(name = "interlyst.spring.webmvc.cors.enable", havingValue = "true",
        matchIfMissing = false)
public class CorsFilter extends AbstractFilter {

    @Value("${interlyst.filter.cors.order:100}")
    private static int ORDER;

    @Value("${interlyst.filter.cors.path:/*}")
    private static String PATH;

    public CorsFilter() {
        super(ORDER, PATH);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        if (servletRequest instanceof HttpServletRequest) {

            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.addHeader("Access-Control-Allow-Headers", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Max-Age", "3600");

            HttpServletRequest request = (HttpServletRequest) servletRequest;

            if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
                response.setStatus(HttpStatus.OK.value());
                return;
            }
        }

        chain.doFilter(servletRequest, servletResponse);
    }

}
