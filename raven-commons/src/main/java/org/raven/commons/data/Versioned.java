package org.raven.commons.data;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.11.09 11:05
 */
public interface Versioned<V> {

    /**
     * @return version
     */
    V getVersion();

    /**
     * @param key version
     */
    void setVersion(V key);
}
