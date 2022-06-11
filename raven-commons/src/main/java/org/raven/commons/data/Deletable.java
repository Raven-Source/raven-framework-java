package org.raven.commons.data;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.9.25
 */
public interface Deletable {

    String DEL = "deleted";

    /**
     * @return logically delete
     */
    Boolean getDeleted();

    /**
     * @param deleted logically delete
     */
    void setDeleted(Boolean deleted);

}
