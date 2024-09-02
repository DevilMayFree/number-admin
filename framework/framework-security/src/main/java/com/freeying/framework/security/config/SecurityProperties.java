package com.freeying.framework.security.config;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SecurityProperties
 * <p>Jwt参数配置</p>
 *
 * @author fx
 */
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityProperties {

    /**
     * Request Headers : Authorization
     */
    private String header;

    /**
     * 令牌前缀，最后留个空格 'Bearer '
     */
    private String tokenStartWith;

    /**
     * 必须使用最少88位的Base64对该令牌进行编码
     */
    private String base64Secret;

    /**
     * 令牌过期时间 此处单位/秒
     */
    private Long tokenValidityInSeconds;

    /**
     * 在线用户 key，根据 key 查询 redis 中在线用户的数据
     */
    private String onlineKey;

    /**
     * token 续期检查
     */
    private Long detect;

    /**
     * 续期时间
     */
    private Long renew;

    /**
     * RSA私钥
     */
    private String rsaPrivateKey;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setTokenStartWith(String tokenStartWith) {
        this.tokenStartWith = tokenStartWith;
    }

    public String getBase64Secret() {
        return base64Secret;
    }

    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public Long getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

    public void setTokenValidityInSeconds(Long tokenValidityInSeconds) {
        this.tokenValidityInSeconds = tokenValidityInSeconds;
    }

    public String getOnlineKey() {
        return onlineKey;
    }

    public void setOnlineKey(String onlineKey) {
        this.onlineKey = onlineKey;
    }

    public Long getDetect() {
        return detect;
    }

    public void setDetect(Long detect) {
        this.detect = detect;
    }

    public Long getRenew() {
        return renew;
    }

    public void setRenew(Long renew) {
        this.renew = renew;
    }

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("header", header)
                .append("tokenStartWith", tokenStartWith)
                .append("base64Secret", base64Secret)
                .append("tokenValidityInSeconds", tokenValidityInSeconds)
                .append("onlineKey", onlineKey)
                .append("detect", detect)
                .append("renew", renew)
                .append("rsaPrivateKey", rsaPrivateKey)
                .toString();
    }
}
