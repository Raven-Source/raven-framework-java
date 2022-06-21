package org.raven.spring.webmvc.filters;

import lombok.Getter;
import org.springframework.boot.web.servlet.filter.OrderedFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

/**
 * @author by yanfeng
 * date 2021/10/3 22:50
 */
public abstract class AbstractFilter implements OrderedFilter {

    @Getter
    protected int order;

    @Getter
    protected String path;

    public AbstractFilter(int order, String path) {
        this.order = order;
        this.path = path;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
