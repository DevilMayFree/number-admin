package com.freeying.common.core.mqtt;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * MqttCode
 * <p>mqtt返回数据的编码枚举</p>
 *
 * @author fx
 */
public enum MqttCode implements IMqttCode {

    /**
     * 操作成功
     */
    SUCCESS("0", "success"),

    /**
     * 业务异常
     */
    ERROR("1", "error"),

    /**
     * 消息解析异常
     */
    PARSE_ERROR("2", "message parse error"),

    /**
     * 不支持的执行方法
     */
    NOT_SUPPORTED_METHOD("3", "not supported method"),

    /**
     * 非法topic
     */
    ILLEGAL_TOPIC("4", "illegal topic"),

    /**
     * 非法请求参数
     */
    ILLEGAL_PARAMS("5", "illegal params"),

    /**
     * 查无此产品对应的物模型
     */
    NOT_FOUND_THING_MODEL("6", "not found thing model"),

    /**
     * 该物模型空属性
     */
    EMPTY_PROPERTY("7", "empty property"),

    /**
     * 包含非法 identifier
     */
    ILLEGAL_IDENTIFIER("8", "illegal identifier"),

    /**
     * Topic包含非法 id
     */
    TOPIC_ILLEGAL_ID("9", "topic illegal id"),

    /**
     * 事件输出参数对象不合法
     */
    ILLEGAL_EVENT_OUTPUT("10", "illegal event output"),

    /**
     * 属性非法输入,数据类型不正确
     */
    PROPERTY_ILLEGAL_DATA_TYPE("11", "property illegal data type"),

    /**
     * 属性入参非法
     */
    PROPERTY_INVALID_VALUE("12", "property invalid value"),

    /**
     * 事件入参非法
     */
    EVENT_INVALID_VALUE("13", "event invalid value"),
    ;

    /**
     * code编码
     */
    private final String code;
    /**
     * 中文信息描述
     */
    private final String message;

    MqttCode(String code, String message) {
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

}
