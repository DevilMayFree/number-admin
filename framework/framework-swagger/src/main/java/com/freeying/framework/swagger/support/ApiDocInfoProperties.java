package com.freeying.framework.swagger.support;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SwaggerProperties
 *
 * @author fx
 */
@ConfigurationProperties(prefix = "springdoc.info")
public class ApiDocInfoProperties {

    /**
     * API文档标题
     */
    private String title;

    /**
     * API文档版本
     */
    private String version;

    /**
     * API文档描述
     */
    private String description;

    /**
     * 联系人信息
     */
    private Contact contact;

    /**
     * 许可证信息
     */
    private License license;

    /**
     * OAuth2 认证 endpoint地址, 默认127.0.0.1:8080/auth/oauth2/token
     */
    private String oauthUrl = "127.0.0.1:8080/auth/oauth2/token";

    public static class Contact {
        /**
         * 联系人姓名
         */
        private String name;
        /**
         * 联系人主页
         */
        private String url;
        /**
         * 联系人邮箱
         */
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    /**
     * 许可证信息
     */
    public static class License {
        /**
         * 许可证名称
         */
        private String name;
        /**
         * 许可证URL
         */
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public String getOauthUrl() {
        return oauthUrl;
    }

    public void setOauthUrl(String oauthUrl) {
        this.oauthUrl = oauthUrl;
    }
}
