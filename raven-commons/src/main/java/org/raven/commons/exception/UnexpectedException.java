package org.raven.commons.exception;

/**
 * @author yi.liang
 */
public class UnexpectedException extends RuntimeException {
    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(String message, Exception cause) {
        super(message, cause);
    }
}

