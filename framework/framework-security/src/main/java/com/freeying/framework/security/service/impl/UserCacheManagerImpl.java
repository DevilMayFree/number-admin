package com.freeying.framework.security.service.impl;

import com.freeying.framework.cache.service.RedisService;
import com.freeying.framework.security.core.JwtUserDetails;
import com.freeying.framework.security.service.UserCacheManager;

public class UserCacheManagerImpl implements UserCacheManager {

    private final RedisService redisService;

    public UserCacheManagerImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public JwtUserDetails getUserDetails(String username) {
        return (JwtUserDetails) redisService.get(username);
    }

    @Override
    public void addUserDetails(String username, JwtUserDetails userDetails) {
        redisService.set(username, userDetails);
    }

    @Override
    public void cleanUserDetails(String username) {
        redisService.del(username);
    }
}
