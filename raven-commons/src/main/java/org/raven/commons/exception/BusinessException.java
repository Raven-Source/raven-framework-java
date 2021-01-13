package org.raven.commons.exception;

import lombok.Getter;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.10.28 15:29
 */
public class BusinessException extends RuntimeException {

    @Getter
    private String code;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

}
