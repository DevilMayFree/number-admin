package com.freeying.framework.security.domain;

import com.freeying.common.core.entity.DTO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * OnlineUserDTO
 * <p>在线用户对象</p>
 *
 * @author fx
 */
public class OnlineUserDTO extends DTO {

    private Long id;

    private String username;

    private String browser;

    private String ip;

    private String address;

    private String os;

    private String token;

    private LocalDateTime loginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("browser", browser)
                .append("ip", ip)
                .append("address", address)
                .append("os", os)
                .append("token", token)
                .append("loginTime", loginTime)
                .toString();
    }
}
