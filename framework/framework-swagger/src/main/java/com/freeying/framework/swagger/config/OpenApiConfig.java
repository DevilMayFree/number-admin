package com.freeying.framework.swagger.config;

import com.freeying.framework.swagger.support.ApiDocInfoProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * OpenAPI 配置类
 *
 * @author fx
 */
@Configuration
@EnableConfigurationProperties(ApiDocInfoProperties.class)
public class OpenApiConfig {

    /**
     * API 文档信息属性
     */
    private final ApiDocInfoProperties apiDocInfoProperties;

    public OpenApiConfig(ApiDocInfoProperties apiDocInfoProperties) {
        this.apiDocInfoProperties = apiDocInfoProperties;
    }


    /**
     * OpenAPI 配置（元信息、安全协议）
     */
    @Bean
    public OpenAPI apiInfo() {
        String oauthUrl = apiDocInfoProperties.getOauthUrl();
        OAuthFlows passwordOAuthFlows = new OAuthFlows().password(new OAuthFlow().tokenUrl(oauthUrl).refreshUrl(oauthUrl));

        SecurityScheme securityScheme = new SecurityScheme()
                // OAuth2 授权模式
                .type(SecurityScheme.Type.OAUTH2)
                .name(HttpHeaders.AUTHORIZATION)
                .flows(passwordOAuthFlows)
                // 安全模式使用Bearer令牌（即JWT）
                .in(SecurityScheme.In.HEADER)
                .scheme("Bearer")
                .bearerFormat("JWT");

        Info info = new Info()
                .title(apiDocInfoProperties.getTitle())
                .version(apiDocInfoProperties.getVersion())
                .description(apiDocInfoProperties.getDescription())
                .contact(new Contact()
                        .name(apiDocInfoProperties.getContact().getName())
                        .url(apiDocInfoProperties.getContact().getUrl())
                        .email(apiDocInfoProperties.getContact().getEmail()))
                .license(new License().name(apiDocInfoProperties.getLicense().getName())
                        .url(apiDocInfoProperties.getLicense().getUrl()));

        return new OpenAPI()
                .components(new Components().addSecuritySchemes(HttpHeaders.AUTHORIZATION, securityScheme))
                // 接口全局添加 Authorization 参数
                .addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION))
                // 接口文档信息
                .info(info);
    }

}
