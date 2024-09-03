package com.freeying.admin.sys.service.impl;

import com.freeying.common.core.constant.SystemConstants;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.common.core.exception.ServiceException;
import com.freeying.common.core.utils.DataConverter;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.DataCheck;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysRoleCommand;
import com.freeying.admin.sys.domain.command.SysUserAuthCommand;
import com.freeying.admin.sys.domain.command.SysUserRoleCommand;
import com.freeying.admin.sys.domain.dto.SysRoleDTO;
import com.freeying.admin.sys.domain.po.SysRole;
import com.freeying.admin.sys.domain.query.SysRoleListQuery;
import com.freeying.admin.sys.domain.query.SysRolePageQuery;
import com.freeying.admin.sys.mapper.SysRoleMapper;
import com.freeying.admin.sys.mapper.SysRoleResourceMapper;
import com.freeying.admin.sys.mapper.SysUserRoleMapper;
import com.freeying.admin.sys.service.SysRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleResourceMapper sysRoleResourceMapper;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper,
                              SysUserRoleMapper sysUserRoleMapper,
                              SysRoleResourceMapper sysRoleResourceMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysRoleResourceMapper = sysRoleResourceMapper;
    }

    @Override
    public PageInfo<SysRoleDTO> page(SysRolePageQuery qry) {
        PageInfo<SysRole> pageInfo = sysRoleMapper.selectSysRolePage(qry.getPageQuery(), qry);
        return DataConverter.converter(sysRole -> {
            SysRoleDTO dto = new SysRoleDTO();
            BeanUtils.copyProperties(sysRole, dto);
            return dto;
        }, pageInfo);
    }

    @Override
    public SysRoleDTO selectRoleById(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        SysRoleDTO dto = new SysRoleDTO();
        if (sysRole != null) {
            BeanUtils.copyProperties(sysRole, dto);
        }
        return dto;
    }

    @Override
    public List<SysRoleDTO> list(SysRoleListQuery qry) {
        List<SysRole> list = sysRoleMapper.selectSysRoleListAll();
        List<SysRoleDTO> dtoList = DataConverter.converter(sysRole -> {
            SysRoleDTO dto = new SysRoleDTO();
            BeanUtils.copyProperties(sysRole, dto);
            return dto;
        }, list);
        if (StringUtils.isNotBlank(qry.getUserId())) {
            List<String> roleCodeList = sysRoleMapper.selectRoleCodeByUserId(Long.valueOf(qry.getUserId()));
            if (!CollectionUtils.isEmpty(roleCodeList) && roleCodeList.contains(SystemConstants.ROLE_ADMIN)) {
                return dtoList;
            }
        }

        return dtoList.stream()
                .filter(i -> !SystemConstants.ROLE_ADMIN.equals(i.getCode()))
                .toList();
    }

    @Override
    public List<SysRoleDTO> selectRoleByUserId(Long userId) {
        List<SysRole> list = sysRoleMapper.selectSysRoleByUserId(userId);
        return DataConverter.converter(sysRole -> {
            SysRoleDTO dto = new SysRoleDTO();
            BeanUtils.copyProperties(sysRole, dto);
            return dto;
        }, list);
    }

    @Override
    public List<SysRoleDTO> selectRoleAllByFlag(Long userId) {
        List<SysRole> userRoles = sysRoleMapper.selectSysRoleByUserId(userId);
        List<SysRole> roles = sysRoleMapper.selectSysRoleListAll();

        List<SysRoleDTO> roleDTOList = DataConverter.converter(sysRole -> {
            SysRoleDTO dto = new SysRoleDTO();
            BeanUtils.copyProperties(sysRole, dto);
            return dto;
        }, roles);

        for (SysRoleDTO dto : roleDTOList) {
            for (SysRole userRole : userRoles) {
                if (userRole.getId().equals(dto.getId())) {
                    dto.setFlag(true);
                }
            }
        }
        return roleDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(SysRoleCommand com) {
        checkRoleName(com.getName());
        checkRoleCode(com.getCode());

        SysRole sysRole = toSysRole(com);
        int insert = sysRoleMapper.insert(sysRole);

        int result = 0;
        if (DataCheck.insert(insert)) {
            List<String> resourceIds = com.getResourceIds();
            result = sysRoleResourceMapper.insertRoleResource(sysRole.getId(), resourceIds);
        }

        return DataCheck.batch(com.getResourceIds().size(), result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysRoleCommand com) {
        checkUpdateRole(com);

        SysRole sysRole = toSysRole(com);
        sysRole.setId(Long.valueOf(com.getId()));
        int update = sysRoleMapper.updateById(sysRole);

        return DataCheck.update(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDel(IdCmdList ids) {
        List<Long> longIds = ids.getLongIds();
        int count = 0;
        for (Long id : longIds) {
            checkDelRole(id);
            count += sysRoleMapper.deleteById(id);
        }
        return count == longIds.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean authCancel(SysUserRoleCommand com) {
        Integer delete = sysUserRoleMapper.deleteByUserRole(Long.valueOf(com.getRoleId()), com.getUserIds());
        return DataCheck.delete(delete);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean userAuth(SysUserRoleCommand com) {
        Integer insert = sysUserRoleMapper.authUserRole(Long.valueOf(com.getRoleId()), com.getUserIds());
        return DataCheck.insert(insert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean roleAuth(SysUserAuthCommand com) {
        Long userId = Long.valueOf(com.getUserId());
        sysUserRoleMapper.deleteByUserId(userId);
        Integer insert = sysUserRoleMapper.insertUserRole(userId, com.getRoleIds());
        return DataCheck.insert(insert);
    }

    private void checkRoleName(String name) {
        SysRole roleByName = sysRoleMapper.selectRoleByName(name);
        if (roleByName != null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, String.format("新增角色, 角色名%s已存在", name));
        }
    }

    private void checkRoleCode(String code) {
        SysRole roleByCode = sysRoleMapper.selectRoleByCode(code);
        if (roleByCode != null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, String.format("新增角色, 角色编码%s已存在", code));
        }
    }

    private void checkRoleAdmin(String code) {
        if (SystemConstants.ROLE_ADMIN.equalsIgnoreCase(code)) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "不允许修改超级管理员角色");
        }
    }

    private void checkUpdateRole(SysRoleCommand com) {
        checkRoleAdmin(com.getCode());
        SysRole dbRole = sysRoleMapper.selectById(com.getId());

        if (dbRole == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "角色id不存在");
        }

        if (!dbRole.getName().equals(com.getName())) {
            checkRoleName(com.getName());
        }

        if (!dbRole.getCode().equals(com.getCode())) {
            checkRoleCode(com.getCode());
        }
    }

    private void checkDelRole(Long id) {
        SysRole sysRole = sysRoleMapper.selectById(id);

        if (sysRole == null) {
            throw new ServiceException(String.format("%1$s不存在", id));
        }
        checkRoleAdmin(sysRole.getCode());

        if (sysRoleMapper.countUserRoleByRoleId(sysRole.getId()) > 0) {
            throw new ServiceException(String.format("%1$s已分配,不能删除", sysRole.getName()));
        }
    }

    private SysRole toSysRole(SysRoleCommand com) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(com, role);
        return role;
    }

}
