package com.freeying.common.core.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * BadMqttRequestException
 * <p>
 * Mqtt请求异常
 * </p>
 *
 * @author fx
 */
@SuppressWarnings("squid:S1165")
public class BadMqttRequestException extends RuntimeException {

    /**
     * 错误返回的topic
     */
    private final String topic;

    private Integer status = BAD_REQUEST.value();

    public BadMqttRequestException(String message, String topic) {
        super(message);
        this.topic = topic;
    }

    public BadMqttRequestException(HttpStatus status, String msg, String topic) {
        super(msg);
        this.status = status.value();
        this.topic = topic;
    }

    public Integer getStatus() {
        return status;
    }

    public String getTopic() {
        return topic;
    }
}
