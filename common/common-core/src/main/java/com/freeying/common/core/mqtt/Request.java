package com.freeying.common.core.mqtt;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Request
 * <p>mqtt消息请求类</p>
 *
 * @author fx
 */
public class Request {

    private String id;

    private String version;

    private String method;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("version", version)
                .append("method", method)
                .toString();
    }
}
