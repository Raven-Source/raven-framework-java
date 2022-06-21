package org.raven.commons.exception;

import lombok.Getter;

/**
 * @author yanfeng
 * date 2021.06.16
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
