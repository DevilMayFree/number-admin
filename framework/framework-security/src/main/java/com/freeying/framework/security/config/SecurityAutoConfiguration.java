package com.freeying.framework.security.config;

import com.freeying.framework.cache.service.RedisService;
import com.freeying.framework.security.core.AuthorityService;
import com.freeying.framework.security.core.JwtTokenAuthenticationFilter;
import com.freeying.framework.security.core.TokenProvider;
import com.freeying.framework.security.service.OnlineUserService;
import com.freeying.framework.security.service.UserCacheManager;
import com.freeying.framework.security.service.impl.OnlineUserServiceImpl;
import com.freeying.framework.security.service.impl.UserCacheManagerImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {

    @Bean
    public TokenProvider tokenProvider(SecurityProperties securityProperties) {
        return new TokenProvider(securityProperties);
    }

    @Bean
    public OnlineUserService onlineUserService(
            TokenProvider tokenProvider, RedisService redisService, SecurityProperties securityProperties) {
        return new OnlineUserServiceImpl(tokenProvider, redisService, securityProperties);
    }

    @Bean
    public UserCacheManager userCacheManager(RedisService redisService) {
        return new UserCacheManagerImpl(redisService);
    }

    @Bean
    public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter(
            TokenProvider tokenProvider, UserCacheManager userCacheManager, OnlineUserService onlineUserService) {
        return new JwtTokenAuthenticationFilter(tokenProvider, userCacheManager, onlineUserService);
    }

    @Bean
    public AuthorityService as() {
        return new AuthorityService();
    }

}
