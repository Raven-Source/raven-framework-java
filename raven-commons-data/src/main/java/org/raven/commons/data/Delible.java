package org.raven.commons.data;

/**
 * @author yi.liang
 * @date 2018.9.25
 * @since JDK1.8
 */
public interface Delible {

    /**
     * 是否逻辑删除
     *
     * @return
     */
    boolean isDel();

    /**
     * 是否逻辑删除
     *
     * @return
     */
    void setDel(boolean del);

}
