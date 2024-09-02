package com.freeying.framework.security.core;

import com.freeying.framework.security.utils.SecurityUtil;

import java.util.Arrays;
import java.util.Set;

/**
 * AuthorityService
 * <p>权限校验</p>
 *
 * @author fx
 */
public class AuthorityService {

    public Boolean hasAuthority(String... permissions) {
        // 获取当前用户的所有权限
        Set<String> permissionList = SecurityUtil.getCurrentPermission();

        // 判断当前用户的所有权限是否包含接口上定义的权限
        return Arrays.stream(permissions).anyMatch(permissionList::contains);
    }
}
