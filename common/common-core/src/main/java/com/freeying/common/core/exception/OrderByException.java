package com.freeying.common.core.exception;


import java.sql.SQLException;

/**
 * OrderByException
 *
 * @author fx
 */
public class OrderByException extends SQLException {

    public OrderByException(String message) {
        super(message);
    }

    public OrderByException(Throwable cause) {
        super(cause);
    }

    public OrderByException(String message, Throwable cause) {
        super(message, cause);
    }

    public static OrderByException illegalOrderException(String message) {
        return new OrderByException("IllegalOrderByColumn:" + message);
    }

}
