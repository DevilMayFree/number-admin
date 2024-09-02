package com.freeying.common.core.web;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 业务代码枚举
 *
 * @author fx
 */
public enum ResultCode implements IResultCode {

    /**
     * 操作成功
     */
    SUCCESS("0", "success"),

    /**
     * 业务异常
     */
    ERROR("1", "error"),

    /**
     * 请求错误
     */
    REQUEST_ERROR("2", "request_error"),

    /**
     * 用户端错误,客户端异常A
     */
    CLIENT_ERROR("A0001", "用户端错误"),

    /**
     * 用户注册错误
     */
    REGISTER_ERROR("A0100", "用户注册错误"),

    /**
     * 校验失败
     */
    VALIDATE_ERROR("A1010", "校验失败"),

    /**
     * 登录异常
     */
    LOGIN_ERROR("A0200", "登录异常"),

    /**
     * 账户不存在
     */
    ACCOUNT_NO_EXIST("A0201", "账户不存在"),

    /**
     * 账户被冻结
     */
    ACCOUNT_LOCKED("A0202", "账户被冻结"),

    /**
     * 账户已作废
     */
    ACCOUNT_INVALID("A0203", "账户已作废"),

    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR("A0210", "用户名或密码错误"),

    /**
     * 用户未获得第三方登录授权
     */
    THIRD_PARTY_LOGIN_AUTHORIZATION("A0223", "用户未获得第三方登录授权"),

    /**
     * 用户登录已过期
     */
    LOGIN_EXPIRED("A0230", "用户登录已过期"),

    /**
     * 用户验证码已过期
     */
    CODE_EXPIRED("A0240", "用户验证码已过期"),

    /**
     * 访问权限异常
     */
    AUTHORIZED_ERROR("A0300", "访问权限异常"),

    /**
     * 请求未授权
     */
    NO_AUTHORIZED("A0301", "请求未授权"),

    /**
     * 请求授权中
     */
    AUTHORIZED_ING("A0302", "请求授权中"),

    /**
     * 请求授权被拒绝
     */
    AUTHORIZED_REFUSE("A0303", "请求授权被拒绝"),

    /**
     * 请求授权已过期
     */
    AUTHORIZED_EXPIRED("A0311", "请求授权已过期"),

    /**
     * 无权限使用API
     */
    UN_AUTHORIZED("A0312", "无权限使用API"),

    /**
     * 用户访问被拦截
     */
    ACCESS_BLOCKED("A0320", "用户访问被拦截"),

    /**
     * 黑名单用户
     */
    BLACKLIST_USER("A0321", "黑名单用户"),

    /**
     * 非法IP地址
     */
    ILLEGAL_IP_ADDRESS("A0323", "非法IP地址"),

    /**
     * 网关访问受限
     */
    GATEWAY_BLOCK("A0324", "网关访问受限"),

    /**
     * 用户签名异常
     */
    USER_SIGN_ERROR("A0340", "用户签名异常"),

    /**
     * 用户请求参数错误
     */
    PARAM_TYPE_ERROR("A0400", "用户请求参数错误"),

    /**
     * 请求资源不存在
     */
    RESOURCE_NOT_FOUND("A0401", "请求资源不存在"),

    /**
     * RSA签名异常
     */
    RSA_SIGN_ERROR("A0341", "RSA签名异常"),

    /**
     * 用户重复请求
     */
    USER_REPEAT_REQ("A0506", "用户重复请求"),

    /**
     * 用户上传文件异常
     */
    USER_UPLOAD_ERROR("A0700", "用户上传文件异常"),

    /**
     * 系统执行出错
     */
    SYSTEM_ERROR("B00001", "系统执行出错"),

    /**
     * 系统执行超时
     */
    SYSTEM_TIMEOUT("B0100", "系统执行超时"),

    /**
     * 系统限流
     */
    SYSTEM_LIMIT("B0210", "系统限流"),

    /**
     * 系统功能降级
     */
    SYSTEM_DEGRADE("B0220", "系统功能降级"),

    /**
     * 系统资源异常
     */
    SYSTEM_RESOURCE_EXCEPTION("B0300", "系统资源异常"),

    /**
     * 调用第三方服务出错
     */
    THIRD_PARTY_SERVICE_CALL_EXCEPTION("C0001", "调用第三方服务出错"),

    /**
     * 中间件服务出错
     */
    MIDDLEWARE_SERVICE_CALL_EXCEPTION("C0100", "中间件服务出错"),

    /**
     * 接口不存在
     */
    INTERFACE_NO_FOUND("C0113", "接口不存在"),

    /**
     * 消息服务出错
     */
    MESSAGE_SERVICE_EXCEPTION("C0120", "消息服务出错"),

    /**
     * 缓存服务出错
     */
    CACHE_SERVICE_EXCEPTION("C0130", "缓存服务出错"),

    /**
     * 配置服务出错
     */
    CONFIG_SERVICE_EXCEPTION("C0140", "配置服务出错"),

    /**
     * 网络资源服务出错
     */
    NETWORK_SERVICE_EXCEPTION("C0150", "网络资源服务出错"),

    /**
     * CDN服务出错
     */
    CDN_SERVICE_EXCEPTION("C0152", "CDN服务出错"),

    /**
     * 网关服务出错
     */
    GATEWAY_SERVICE_EXCEPTION("C0154", "网关服务出错"),

    /**
     * 第三方系统执行超时
     */
    THIRD_PARTY_SYSTEM_TIMEOUT("C0200", "第三方系统执行超时"),

    /**
     * 数据库服务出错
     */
    DATABASE_SERVICE_EXCEPTION("C0300", "数据库服务出错"),
    ;

    /**
     * code编码
     */
    private final String code;
    /**
     * 中文信息描述
     */
    private final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .toString();
    }

    public static ResultCode getValue(String code) {
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_ERROR;
    }
}
