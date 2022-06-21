package org.raven.commons.contract;

import lombok.Data;
import org.raven.commons.data.annotation.Ignore;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
@Data
public class ResponseModel<TData, TCode> implements Response<TData, TCode, Extension> {

    private boolean success;

    private TCode code;

    private TData data;

    private String message;

    private Extension extension;

    /**
     *
     */
    public ResponseModel() {
        this.success = true;
        this.extension = new Extension();
    }

    public ResponseModel(boolean success, TData data, String message, TCode code) {

        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
        this.extension = new Extension();
    }

    @Ignore
    public void extend(String key, Object val) {
        this.extension.put(key, val);
    }
}
