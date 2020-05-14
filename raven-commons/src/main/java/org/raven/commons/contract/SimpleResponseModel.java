package org.raven.commons.contract;

import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
public interface SimpleResponseModel {

    List<KeyValue<String, String>> getExtension();
    void setExtension(List<KeyValue<String, String>> extension);
}
