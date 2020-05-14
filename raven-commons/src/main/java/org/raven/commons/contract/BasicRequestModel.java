package org.raven.commons.contract;

import lombok.Data;

import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
@Data
public class BasicRequestModel implements RequestModel {

    private List<KeyValue<String, String>> extension;

    /**
     *
     */
    public BasicRequestModel() {
        extension = new Extension();
    }

}
