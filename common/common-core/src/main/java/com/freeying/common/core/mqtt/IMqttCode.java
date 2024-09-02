package com.freeying.common.core.mqtt;

import java.io.Serializable;

public interface IMqttCode extends Serializable {

    /**
     * 消息
     *
     * @return String
     */
    String getMessage();

    /**
     * 状态码
     *
     * @return String
     */
    String getCode();
}
