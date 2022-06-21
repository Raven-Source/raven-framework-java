package org.raven.commons.contract;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
public interface Response<TData, TCode, TExtension> extends CodeResponse<TCode, TExtension> {
    TData getData();

    void setData(TData data);
}
