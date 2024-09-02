package com.freeying.common.core.mqtt;

import org.apache.commons.lang3.builder.ToStringBuilder;


import java.io.Serializable;

/**
 * Response
 * <p>mqtt消息返回类</p>
 *
 * @author fx
 */
public class Response<T> implements Serializable {

    private String code;

    private String msg;

    @SuppressWarnings("squid:S1948")
    private T data;

    public Response() {
    }

    private Response(IMqttCode mqttCode) {
        this(mqttCode, null, mqttCode.getMessage());
    }

    private Response(IMqttCode mqttCode, String msg) {
        this(mqttCode, null, msg);
    }

    private Response(IMqttCode mqttCode, T data) {
        this(mqttCode, data, mqttCode.getMessage());
    }

    private Response(IMqttCode mqttCode, T data, String msg) {
        this(mqttCode.getCode(), data, msg);
    }

    private Response(String code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Response<T> success() {
        return success(MqttCode.SUCCESS, MqttCode.SUCCESS.getMessage());
    }

    public static <T> Response<T> success(IMqttCode mqttCode) {
        return new Response<>(mqttCode);
    }

    public static <T> Response<T> success(IMqttCode mqttCode, String msg) {
        return new Response<>(mqttCode, msg);
    }

    public static <T> Response<T> success(IMqttCode mqttCode, T data) {
        return new Response<>(mqttCode, data);
    }

    public static <T> Response<T> error() {
        return error(MqttCode.ERROR, MqttCode.ERROR.getMessage());
    }

    public static <T> Response<T> error(IMqttCode mqttCode) {
        return new Response<>(mqttCode);
    }

    public static <T> Response<T> error(IMqttCode mqttCode, String msg) {
        return new Response<>(mqttCode, msg);
    }

    public static <T> Response<T> error(IMqttCode mqttCode, T data) {
        return new Response<>(mqttCode, data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("msg", msg)
                .append("data", data)
                .toString();
    }
}
