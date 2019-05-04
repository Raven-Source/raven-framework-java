package org.raven.commons.data;

/**
 * @author yi.liang
 * @date 2018.11.09 11:05
 * @since JDK1.8
 */
public interface Version<V> {

    /**
     * 获取主键
     *
     * @return primary key
     */
    V getVersion();

    /**
     * 设置主键
     *
     * @param key primary key
     */
    void setVersion(V key);
}
