package com.freeying.framework.security.service;

import com.freeying.framework.security.core.JwtUserDetails;

public interface UserCacheManager {

    JwtUserDetails getUserDetails(String username);

    void addUserDetails(String username, JwtUserDetails userDetails);

    void cleanUserDetails(String username);
}
