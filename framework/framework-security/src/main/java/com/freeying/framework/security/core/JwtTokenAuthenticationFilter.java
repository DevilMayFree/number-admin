package com.freeying.framework.security.core;

import com.freeying.framework.security.domain.OnlineUserDTO;
import com.freeying.framework.security.service.OnlineUserService;
import com.freeying.framework.security.service.UserCacheManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

    private final TokenProvider tokenProvider;
    private final UserCacheManager userCacheManager;
    private final OnlineUserService onlineUserService;

    public JwtTokenAuthenticationFilter(TokenProvider tokenProvider,
                                        UserCacheManager userCacheManager,
                                        OnlineUserService onlineUserService) {
        this.tokenProvider = tokenProvider;
        this.userCacheManager = userCacheManager;
        this.onlineUserService = onlineUserService;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        String token = tokenProvider.resolveToken(request);

        if (checkToken(token)) {
            OnlineUserDTO onlineUserDto = null;
            boolean clearUserCache = false;

            try {
                String loginKey = tokenProvider.loginKey(token);
                onlineUserDto = onlineUserService.getOne(loginKey);
            } catch (JwtException e) {
                log.error(e.getMessage());
                clearUserCache = true;
            } finally {
                if (clearUserCache || Objects.isNull(onlineUserDto)) {
                    userCacheManager.cleanUserDetails(String.valueOf(tokenProvider.getClaims(token).get(TokenProvider.AUTHORITIES_KEY)));
                }
            }

            if (onlineUserDto != null && StringUtils.hasText(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 续期
                onlineUserService.renewCache(token);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        try {
            Claims claims = tokenProvider.getClaims(token);
            return !claims.isEmpty();
        } catch (JwtException e) {
            return false;
        }
    }

}
