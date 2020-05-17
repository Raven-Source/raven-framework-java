package org.raven.commons.contract;

import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
public interface SimpleResponseModel<TExtension> {

    TExtension getExtension();
    void setExtension(TExtension extension);
}
