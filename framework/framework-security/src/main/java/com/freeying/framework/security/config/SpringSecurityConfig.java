package com.freeying.framework.security.config;

import com.freeying.framework.security.core.JwtTokenAuthenticationFilter;
import com.freeying.framework.security.handler.ResourceAccessDeniedHandler;
import com.freeying.framework.security.handler.ResourceAuthExceptionEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

/**
 * SpringSecurityConfig
 *
 * @author fx
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {

    private final CorsFilter corsFilter;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    public SpringSecurityConfig(CorsFilter corsFilter,
                                JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter) {
        this.corsFilter = corsFilter;
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用httpBasic登录
                .httpBasic(AbstractHttpConfigurer::disable)
                // csrf 禁用
                .csrf(AbstractHttpConfigurer::disable)
                // 异常处理
                .exceptionHandling(configurer -> configurer
                        // 认证异常
                        .authenticationEntryPoint(new ResourceAuthExceptionEntryPoint())
                        // 授权异常
                        .accessDeniedHandler(new ResourceAccessDeniedHandler())
                )
                // 禁用表单登录
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                // 防止iframe跨域
                .headers(configurer -> configurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                // session 禁用,采用token方式
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(registry -> registry
                        // options 请求开放
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS, "/**")).permitAll()
                        // swagger 请求开放
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/swagger-ui/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/doc.html")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/webjars/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/*/api-docs/**")).permitAll()
                        // security 登录请求开放
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api/auth/login")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/favicon.ico")).permitAll()
                        // druid 相关
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/druid/**")).permitAll()
                        // mqtt 相关
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api/emqx/auth")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api/emqx/webhook")).permitAll()
                        // for test
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api/test/**")).permitAll()

                        // 其余请求认证
                        .anyRequest().authenticated()
                )
                .addFilterBefore(corsFilter, CorsFilter.class)
                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // 去除 ROLE_ 前缀
        return new GrantedAuthorityDefaults("");
    }
}
