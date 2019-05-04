package org.raven.commons.data;

/**
 * @param <TKey>
 * @author yi.liang
 * @date 2018.9.25
 * @since JDK1.8
 */
public interface Entity<TKey> {

    /**
     * 获取主键
     *
     * @return primary key
     */
    TKey getId();

    /**
     * 设置主键
     *
     * @param key primary key
     */
    void setId(TKey key);

}
