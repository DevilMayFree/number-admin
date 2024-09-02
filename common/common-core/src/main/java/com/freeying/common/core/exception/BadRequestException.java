package com.freeying.common.core.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * BadRequestException
 * <p>
 * 请求异常
 * </p>
 *
 * @author fx
 */
@SuppressWarnings("squid:S1165")
public class BadRequestException extends RuntimeException {

    private Integer status = BAD_REQUEST.value();

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }

    public Integer getStatus() {
        return status;
    }
}
