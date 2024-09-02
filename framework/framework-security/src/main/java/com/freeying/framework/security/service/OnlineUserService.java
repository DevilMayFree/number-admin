package com.freeying.framework.security.service;

import com.freeying.framework.security.core.JwtUserDetails;
import com.freeying.framework.security.domain.OnlineUserDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface OnlineUserService {

    /**
     * 保存登录用户
     *
     * @param jwtUserDetails jwtUserDetails
     * @param token          token
     * @param request        request
     */
    void save(JwtUserDetails jwtUserDetails, String token, HttpServletRequest request);

    /**
     * 根据登录用户key查询在线用户对象
     *
     * @param loginKey loginKey
     * @return OnlineUserDTO
     */
    OnlineUserDTO getOne(String loginKey);

    /**
     * 登出
     *
     * @param token token
     */
    void logout(String token);

    /**
     * 续期OnlineUser
     *
     * @param token token
     */
    void renewCache(String token);

}
