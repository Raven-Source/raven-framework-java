package org.raven.commons.context;


import java.util.Map;

/**
 * @author by yanfeng
 * date 2021/10/3 20:19
 */
public interface Context {

    Map<String, Object> getAttributes();

    Object getAttribute(String name);

    void setAttribute(String name, Object object);

}
