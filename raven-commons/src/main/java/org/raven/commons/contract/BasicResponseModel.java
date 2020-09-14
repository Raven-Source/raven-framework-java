package org.raven.commons.contract;

import lombok.Data;
import org.raven.commons.data.annotation.Ignore;

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

    public BasicResponseModel(TData data, String message, TCode code) {

        this.data = data;
        this.message = message;
        this.code = code;
        this.extension = new Extension();
    }

    @Ignore
    public void extend(String key, Object val) {
        this.extension.put(key, val);
    }
}
