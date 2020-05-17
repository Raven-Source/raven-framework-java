package org.raven.commons.contract;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
public interface CodeResponseModel<TCode, TExtension> extends SimpleResponseModel<TExtension> {

    TCode getCode();
    void setCode(TCode code);
}