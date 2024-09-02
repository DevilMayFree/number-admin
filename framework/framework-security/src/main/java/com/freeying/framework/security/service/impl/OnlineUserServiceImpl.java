package com.freeying.framework.security.service.impl;

import com.freeying.common.core.utils.StringPool;
import com.freeying.framework.cache.service.RedisService;
import com.freeying.framework.security.config.SecurityProperties;
import com.freeying.framework.security.core.JwtUserDetails;
import com.freeying.framework.security.core.TokenProvider;
import com.freeying.framework.security.domain.OnlineUserDTO;
import com.freeying.framework.security.service.OnlineUserService;
import com.freeying.framework.security.utils.EncryptUtil;
import com.freeying.framework.security.utils.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.GeneralSecurityException;
import java.time.LocalDateTime;

public class OnlineUserServiceImpl implements OnlineUserService {
    private static final Logger log = LoggerFactory.getLogger(OnlineUserServiceImpl.class);

    private final TokenProvider tokenProvider;
    private final RedisService redisService;
    private final SecurityProperties securityProperties;

    public OnlineUserServiceImpl(TokenProvider tokenProvider, RedisService redisService, SecurityProperties securityProperties) {
        this.tokenProvider = tokenProvider;
        this.redisService = redisService;
        this.securityProperties = securityProperties;
    }

    @Override
    public void save(JwtUserDetails jwtUserDetails, String token, HttpServletRequest request) {
        String ip = HttpUtil.getIp(request);
        String browser = HttpUtil.getBrowser(request);
        String os = HttpUtil.getOs(request);
        String cityInfo = HttpUtil.getCityInfo(ip);

        String tokenKey = StringPool.EMPTY;
        try {
            tokenKey = EncryptUtil.desEncrypt(token);
        } catch (GeneralSecurityException e) {
            log.error(e.getMessage(), e);
        }

        OnlineUserDTO dto = new OnlineUserDTO();
        dto.setId(jwtUserDetails.getId());
        dto.setUsername(jwtUserDetails.getUsername());
        dto.setIp(ip);
        dto.setAddress(cityInfo);
        dto.setBrowser(browser);
        dto.setOs(os);
        dto.setToken(tokenKey);
        dto.setLoginTime(LocalDateTime.now());
        String loginKey = tokenProvider.loginKey(token);

        redisService.set(loginKey, dto, securityProperties.getTokenValidityInSeconds());
    }

    @Override
    public OnlineUserDTO getOne(String loginKey) {
        return (OnlineUserDTO) redisService.get(loginKey);
    }

    @Override
    public void logout(String token) {
        String loginKey = tokenProvider.loginKey(token);
        redisService.del(loginKey);
    }

    @Override
    public void renewCache(String token) {
        String loginKey = tokenProvider.loginKey(token);
        redisService.expire(loginKey, securityProperties.getTokenValidityInSeconds());
    }

}
