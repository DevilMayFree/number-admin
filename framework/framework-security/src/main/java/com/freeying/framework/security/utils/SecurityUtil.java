package com.freeying.framework.security.utils;

import com.freeying.common.core.constant.SystemConstants;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.common.core.utils.SpringContextHolder;
import com.freeying.framework.security.core.JwtUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

/**
 * SecurityUtil
 * <p>Security工具类</p>
 *
 * @author fx
 */
public final class SecurityUtil {

    private SecurityUtil() {
    }

    private static JwtUserDetails getCurrentUser() {
        UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
        JwtUserDetails jwtUserDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(getCurrentUsername());
        jwtUserDetails.setPassword("*****");
        return jwtUserDetails;
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        throw new BadRequestException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        JwtUserDetails userDetails = getCurrentUser();
        return userDetails.getId();
    }

    /**
     * 获取当前用户角色编码列表
     *
     * @return 用户权限
     */
    public static Set<String> getCurrentRole() {
        return getCurrentUser().getRoles();
    }

    /**
     * 获取当前用户权限编码列表
     *
     * @return 用户权限
     */
    public static Set<String> getCurrentPermission() {
        return getCurrentUser().getPermissions();
    }

    /**
     * 判断当前用户是否是管理员
     *
     * @return boolean
     */
    public static boolean isAdmin() {
        Set<String> currentRole = getCurrentRole();
        if (currentRole.isEmpty()) {
            return false;
        }
        return currentRole.contains(SystemConstants.ROLE_ADMIN);
    }

}
