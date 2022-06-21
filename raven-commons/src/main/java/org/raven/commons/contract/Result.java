package org.raven.commons.contract;

/**
 * @author yanfeng
 * @since JDK11
 * date 2021.07.05 17:16
 */
@SuppressWarnings("unchecked")
public abstract class Result {

    public static final String SUCCESS_MESSAGE = "success";
    public static final String FAIL_MESSAGE = "fail";
    public static final String EMPTY = "";
    public static final String DEFAULT_CODE = "0";

    public static <TData> ResponseModel<TData> ok(TData data, String message, String code) {
        return new ResponseModel<>(data, message, code, true);
    }

    public static <TData> ResponseModel<TData> ok(TData data, String message) {
        return ok(data, message, DEFAULT_CODE);
    }

    public static <TData> ResponseModel<TData> ok(TData data) {
        return ok(data, SUCCESS_MESSAGE);
    }

    public static ResponseModel ok(String message) {
        return ok(null, message, DEFAULT_CODE);
    }

    public static ResponseModel ok() {
        return ok(null);
    }

    public static <TData> ResponseModel<TData> fail(TData data, String message, String code) {
        return new ResponseModel<>(data, message, code, false);
    }

    public static ResponseModel fail(String message, String code) {
        return fail(null, message, code);
    }

    public static ResponseModel fail(String message) {
        return fail(message, DEFAULT_CODE);
    }

}
