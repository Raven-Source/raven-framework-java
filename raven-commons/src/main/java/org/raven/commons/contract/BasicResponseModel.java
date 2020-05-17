package org.raven.commons.contract;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
@Data
public class BasicResponseModel<TData, TCode> implements ResponseModel<TData, TCode, Extension> {

    private TCode code;

    private TData data;

    private Extension extension;

    private String message;

    /**
     *
     */
    public BasicResponseModel() {
        extension = new Extension();
    }

}
