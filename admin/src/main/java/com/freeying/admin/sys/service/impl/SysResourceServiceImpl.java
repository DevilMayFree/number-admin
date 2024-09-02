package com.freeying.admin.sys.service.impl;

import com.freeying.common.core.constant.CacheConstants;
import com.freeying.common.core.constant.SystemConstants;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.framework.cache.annotation.AppCacheable;
import com.freeying.framework.cache.service.RedisService;
import com.freeying.framework.data.core.BasePO;
import com.freeying.framework.data.core.DataCheck;
import com.freeying.framework.data.core.IdCmd;
import com.freeying.framework.security.utils.SecurityUtil;
import com.freeying.admin.sys.domain.command.SysResourceCommand;
import com.freeying.admin.sys.domain.dto.RouteDTO;
import com.freeying.admin.sys.domain.dto.SysResourceDTO;
import com.freeying.admin.sys.domain.enums.ResourceTypeEnum;
import com.freeying.admin.sys.domain.po.SysResource;
import com.freeying.admin.sys.domain.po.SysRole;
import com.freeying.admin.sys.domain.query.SysResourceListQuery;
import com.freeying.admin.sys.mapper.SysResourceMapper;
import com.freeying.admin.sys.mapper.SysRoleMapper;
import com.freeying.admin.sys.service.SysResourceService;
import com.freeying.admin.sys.support.RouteHandler;
import com.freeying.admin.sys.support.TreeNodeHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysResourceServiceImpl implements SysResourceService {

    private final SysResourceMapper sysResourceMapper;
    private final SysRoleMapper sysRoleMapper;
    private final RedisService redisService;

    public SysResourceServiceImpl(SysResourceMapper sysResourceMapper,
                                  SysRoleMapper sysRoleMapper,
                                  RedisService redisService) {
        this.sysResourceMapper = sysResourceMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.redisService = redisService;
    }

    @Override
    @AppCacheable(value = "sys:resource:route", key = "#userId")
    public List<RouteDTO> listRoutes(Long userId) {
        List<SysRole> sysRoles = sysRoleMapper.selectSysRoleByUserId(userId);
        Set<Long> roleIds = sysRoles.stream().map(BasePO::getId).collect(Collectors.toSet());
        List<SysResource> poList = sysResourceMapper.selectResource(roleIds, ResourceTypeEnum.getMenus());
        return RouteHandler.recurRoutes(SystemConstants.ROOT_NODE_ID, poList);
    }

    @Override
    @AppCacheable(value = "sys:resource:treeSelect", key = "#userId")
    public List<SysResourceDTO> treeSelect(Long userId) {
        List<SysRole> sysRoles = sysRoleMapper.selectSysRoleByUserId(userId);
        Set<Long> roleIds = sysRoles.stream().map(BasePO::getId).collect(Collectors.toSet());
        List<SysResourceDTO> poList = sysResourceMapper.selectResourceDTO(roleIds, ResourceTypeEnum.getTreeSelect());
        return TreeNodeHandler.recurNodes(SystemConstants.ROOT_NODE_ID, poList);
    }

    @Override
    @AppCacheable(value = "sys:resource:roleSelect", key = "#roleId")
    public List<Long> roleSelect(Long roleId) {
        return sysResourceMapper.selectResourceIdByRoleId(roleId);
    }

    @Override
    public List<SysResourceDTO> list(SysResourceListQuery qry) {
        Set<String> currentRole = SecurityUtil.getCurrentRole();
        if (currentRole != null && currentRole.contains(SystemConstants.ROLE_ADMIN)) {
            return sysResourceMapper.selectResourceDTOListByAdmin(ResourceTypeEnum.getTreeSelect(), qry);
        } else {
            Long userId = SecurityUtil.getCurrentUserId();
            List<SysRole> sysRoles = sysRoleMapper.selectSysRoleByUserId(userId);
            Set<Long> roleIds = sysRoles.stream().map(BasePO::getId).collect(Collectors.toSet());
            return sysResourceMapper.selectResourceDTOList(roleIds, ResourceTypeEnum.getTreeSelect(), qry);
        }
    }

    @Override
    public SysResourceDTO selectResourceById(Long id) {
        SysResource sysResource = sysResourceMapper.selectById(id);
        SysResourceDTO dto = new SysResourceDTO();
        if (sysResource != null) {
            BeanUtils.copyProperties(sysResource, dto);
        }
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(SysResourceCommand com) {
        checkResourceName(com);
        checkResourceLimit(com);
        SysResource sysResource = toSysResource(com);
        sysResource.setPid(Long.valueOf(com.getPid()));
        int insert = sysResourceMapper.insert(sysResource);
        return DataCheck.insert(insert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysResourceCommand com) {
        SysResource dbResource = sysResourceMapper.selectSysResourceByNamePid(com.getName(), Long.valueOf(com.getPid()));
        if (dbResource != null && (!com.getId().equals(String.valueOf(dbResource.getId())))) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "该上级资源下已有同名资源");
        }
        checkResourceLimit(com);
        SysResource sysResource = toSysResource(com);
        sysResource.setPid(Long.valueOf(com.getPid()));
        sysResource.setId(Long.valueOf(com.getId()));
        int update = sysResourceMapper.updateById(sysResource);

        refreshCache();
        return DataCheck.update(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean del(IdCmd id) {
        SysResource dbResource = sysResourceMapper.selectSysResourceByPid(id.getLongId());
        if (dbResource != null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "该资源还包含有下级资源");
        }

        int delete = sysResourceMapper.deleteById(id.getLongId());
        refreshCache();
        return DataCheck.delete(delete);
    }

    private void checkResourceName(SysResourceCommand com) {
        SysResource sysResource = sysResourceMapper.selectSysResourceByNamePid(com.getName(), Long.valueOf(com.getPid()));
        if (sysResource != null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "该上级资源下已有同名资源");
        }
    }

    private void checkResourceLimit(SysResourceCommand com) {
        Long pid = Long.valueOf(com.getPid());
        if (SystemConstants.ROOT_NODE_ID.equals(pid)) {
            return;
        }
        SysResource pidResource = sysResourceMapper.selectById(pid);

        if (pidResource == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "不存在该上级资源,pid不合法");
        }

        Integer type = pidResource.getType();
        if (!ResourceTypeEnum.isValidLimit(type, com.getType())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "该上级资源下不能存在该类型资源");
        }
    }

    private SysResource toSysResource(SysResourceCommand com) {
        SysResource sysResource = new SysResource();
        BeanUtils.copyProperties(com, sysResource);
        return sysResource;
    }

    private void refreshCache() {
        Set<String> keys = redisService.keys(CacheConstants.SYS_RESOURCE_KEY);
        redisService.del(keys);
    }
}
