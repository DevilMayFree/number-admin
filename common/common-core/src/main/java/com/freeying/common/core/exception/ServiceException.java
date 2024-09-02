package com.freeying.common.core.exception;

/**
 * ServiceException
 * <p>
 * 业务异常
 * </p>
 *
 * @author fx
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
