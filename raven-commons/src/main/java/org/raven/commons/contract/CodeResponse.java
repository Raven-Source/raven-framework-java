package org.raven.commons.contract;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
public interface CodeResponse<TCode, TExtension> extends SimpleResponse<TExtension> {

    TCode getCode();
    void setCode(TCode code);
}