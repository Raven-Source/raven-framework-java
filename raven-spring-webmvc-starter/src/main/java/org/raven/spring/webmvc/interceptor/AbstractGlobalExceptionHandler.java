package org.raven.spring.webmvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.raven.commons.contract.ResponseModel;
import org.raven.commons.contract.Result;
import org.raven.commons.exception.BusinessException;
import org.raven.spring.web.util.MessageUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
public abstract class AbstractGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // BusinessException
    @ExceptionHandler({
            BusinessException.class
    })
    protected ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {

        String msg = String.format("request url: %s\r\n%s", getRequestURI(request), ex.getMessage());
        log.warn(msg, ex);
        HttpStatus status = HttpStatus.OK;

        return handleExceptionInternal(ex, null, null, status, request);
    }

    // IllegalArgumentException
    @ExceptionHandler({
            IllegalArgumentException.class
    })
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        String msg = String.format("request url: %s\r\n%s", getRequestURI(request), ex.getMessage());
        log.warn(msg, ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return handleExceptionInternal(ex, null, null, status, request);
    }

    // ConstraintViolationException
    @ExceptionHandler({
            ConstraintViolationException.class,
    })
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {

        String msg = String.format("request url: %s\r\n%s", getRequestURI(request), ex.getMessage());
        log.warn(msg, ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return handleExceptionInternal(ex, null, null, status, request);
    }

    // Exception
    @ExceptionHandler({
            Exception.class
    })
    protected ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {

        String msg = String.format("request url: %s\r\n%s", getRequestURI(request), ex.getMessage());
        log.error(msg, ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return handleExceptionInternal(ex, null, null, status, request);
    }

    // handleExceptionInternal
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {

            body = buildBody(ex, status);
        }

        if (headers == null) {
            headers = new HttpHeaders();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }


    private ResponseModel buildBody(Exception ex, HttpStatus status) {

        String message = null;
        String code = String.valueOf(status.value());
        if (ex instanceof ConstraintViolationException) {
            message = MessageUtil.getMessage((ConstraintViolationException) ex);
        } else if (ex instanceof MethodArgumentNotValidException) {
            message = MessageUtil.getMessage((MethodArgumentNotValidException) ex);
        } else if (ex instanceof BusinessException) {
            message = ex.getMessage();
            code = ((BusinessException) ex).getCode();
        } else {
            message = StringUtils.isNoneBlank(ex.getMessage()) ? ex.getMessage() : ex.toString();
        }

        return Result.fail(message, code);
    }

    private String getRequestURI(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            return ((ServletWebRequest) request).getRequest().getRequestURI();
        }

        return StringUtils.EMPTY;
    }

}