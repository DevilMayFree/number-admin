package com.freeying.common.core.web;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.ObjectUtils;


import java.io.Serializable;
import java.util.Optional;

/**
 * 响应信息主体
 *
 * @author fx
 */
@Schema(description = "返回信息")
public class Result<T> implements Serializable {

    /**
     * 状态码
     */
    @Schema(description = "状态码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    /**
     * 消息
     */
    @Schema(description = "消息", requiredMode = Schema.RequiredMode.REQUIRED)
    private String msg;

    /**
     * 数据
     */
    @SuppressWarnings("squid:S1948")
    @Schema(description = "数据")
    private T data;

    public Result() {
    }

    private Result(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private Result(IResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private Result(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private Result(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private Result(String code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isSuccess(Result<?> result) {
        return Optional.ofNullable(result).map(x -> ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.getCode(), x.code)).orElse(Boolean.FALSE);
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isNotSuccess(Result<?> result) {
        return !Result.isSuccess(result);
    }

    /**
     * 返回Result
     *
     * @param <T> T
     * @return Result
     */
    public static <T> Result<T> success() {
        return success(ResultCode.SUCCESS, ResultCode.SUCCESS.getMessage());
    }

    /**
     * 返回Result
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(IResultCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 返回Result
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(IResultCode resultCode, String msg) {
        return new Result<>(resultCode, msg);
    }

    /**
     * 返回Result
     *
     * @param resultCode 业务代码
     * @param data       数据
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(IResultCode resultCode, T data) {
        return new Result<>(resultCode, data);
    }

    /**
     * 返回Result
     *
     * @param resultCode 业务代码
     * @param data       数据
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> success(IResultCode resultCode, T data, String msg) {
        return new Result<>(resultCode, data, msg);
    }

    /**
     * 返回Result
     *
     * @param msg 消息
     * @param <T> T
     * @return Result
     */
    public static <T> Result<T> success(String msg) {
        return new Result<>(ResultCode.SUCCESS, msg);
    }

    /**
     * 返回Result
     *
     * @param data 数据
     * @param <T>  T
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return success(data, ResultCode.SUCCESS.getMessage());
    }

    /**
     * 返回Result
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T
     * @return Result
     */
    public static <T> Result<T> success(T data, String msg) {
        return success(ResultCode.SUCCESS.getCode(), data, msg);
    }

    /**
     * 返回Result
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>  T
     * @return Result
     */
    public static <T> Result<T> success(String code, T data, String msg) {
        return new Result<>(code, data, msg);
    }

    /**
     * 返回Result
     *
     * @param <T> T
     * @return Result
     */
    public static <T> Result<T> error() {
        return error(ResultCode.ERROR, ResultCode.ERROR.getMessage());
    }

    /**
     * 返回Result
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> error(IResultCode resultCode) {
        return new Result<>(resultCode);
    }

    /**
     * 返回Result
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> error(IResultCode resultCode, String msg) {
        return new Result<>(resultCode, msg);
    }

    /**
     * 返回Result
     *
     * @param resultCode 业务代码
     * @param data       数据
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> error(IResultCode resultCode, T data) {
        return new Result<>(resultCode, data);
    }

    /**
     * 返回Result
     *
     * @param resultCode 业务代码
     * @param data       数据
     * @param <T>        T 泛型标记
     * @return Result
     */
    public static <T> Result<T> error(IResultCode resultCode, T data, String msg) {
        return new Result<>(resultCode, data, msg);
    }

    /**
     * 返回Result
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return Result
     */
    public static <T> Result<T> error(String msg) {
        return new Result<>(ResultCode.ERROR, msg);
    }

    /**
     * 返回Result
     *
     * @param data 数据
     * @param <T>  T
     * @return Result
     */
    public static <T> Result<T> error(T data) {
        return error(data, ResultCode.ERROR.getMessage());
    }

    /**
     * 返回Result
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T
     * @return Result
     */
    public static <T> Result<T> error(T data, String msg) {
        return error(ResultCode.SYSTEM_ERROR.getCode(), data, msg);
    }

    /**
     * 返回Result
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>  T
     * @return Result
     */
    public static <T> Result<T> error(String code, T data, String msg) {
        return new Result<>(code, data, msg);
    }

    /**
     * 返回Result
     *
     * @param flag 成功状态
     * @return R
     */
    public static <T> Result<T> status(boolean flag) {
        return flag ? success(ResultCode.SUCCESS) : error(ResultCode.ERROR);
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
