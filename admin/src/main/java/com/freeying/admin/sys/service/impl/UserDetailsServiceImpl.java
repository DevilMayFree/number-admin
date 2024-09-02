package com.freeying.admin.sys.service.impl;

import com.freeying.framework.cache.annotation.AppCacheable;
import com.freeying.framework.security.core.JwtUserDetails;
import com.freeying.admin.sys.domain.enums.ResourceTypeEnum;
import com.freeying.admin.sys.domain.po.SysResource;
import com.freeying.admin.sys.domain.po.SysRole;
import com.freeying.admin.sys.domain.po.SysUser;
import com.freeying.admin.sys.mapper.SysResourceMapper;
import com.freeying.admin.sys.mapper.SysRoleMapper;
import com.freeying.admin.sys.mapper.SysUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysResourceMapper sysResourceMapper;

    public UserDetailsServiceImpl(SysUserMapper sysUserMapper,
                                  SysRoleMapper sysRoleMapper,
                                  SysResourceMapper sysResourceMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysResourceMapper = sysResourceMapper;
    }

    @Override
    @AppCacheable(value = "auth:sys_user_detail", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername username:{}", username);

        SysUser sysUser = sysUserMapper.selectSysUserByUserName(StringUtils.trim(username));

        if (sysUser == null) {
            throw new UsernameNotFoundException(username + " not found");
        }

        List<SysRole> sysRoles = sysRoleMapper.selectSysRoleByUserId(sysUser.getId());

        Set<String> roles = new HashSet<>();
        Set<Long> roleIds = sysRoles.stream().map(i -> {
            roles.add(i.getCode());
            return i.getId();
        }).collect(Collectors.toSet());

        List<SysResource> sysResources = sysResourceMapper.selectResource(roleIds, ResourceTypeEnum.getPermission());

        Set<String> permission = sysResources.stream()
                .map(SysResource::getCode)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());

        JwtUserDetails userDetails = new JwtUserDetails();
        userDetails.setId(sysUser.getId());
        userDetails.setUsername(sysUser.getUsername());
        userDetails.setPassword(sysUser.getPassword());
        userDetails.setStatus(sysUser.getStatus());
        userDetails.setRoles(roles);
        userDetails.setPermissions(permission);

        return userDetails;
    }

}
