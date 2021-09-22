package org.raven.commons.data;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.9.25
 */
public interface Deletable {

    String DEL = "del";

    /**
     * @return logically del
     */
    boolean isDel();

    /**
     * @param del logically del
     */
    void setDel(boolean del);

}
