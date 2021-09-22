package org.raven.commons.data;

/**
 * @param <TKey> Key type
 * @author yi.liang
 * @since JDK1.8
 * date 2018.9.25
 */
public interface Entity<TKey> {

    String ID = "id";

    /**
     * @return primary key
     */
    TKey getId();

    /**
     * @param key primary key
     */
    void setId(TKey key);

}
