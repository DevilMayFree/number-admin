package com.freeying.admin.sys.service.impl;

import com.freeying.common.core.constant.CommonConstants;
import com.freeying.common.core.constant.SystemConstants;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.common.core.exception.ServiceException;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.DataCheck;
import com.freeying.framework.data.core.IdCmd;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysUserCommand;
import com.freeying.admin.sys.domain.dto.SysUserDTO;
import com.freeying.admin.sys.domain.po.SysUser;
import com.freeying.admin.sys.domain.query.SysAuthUserPageQuery;
import com.freeying.admin.sys.domain.query.SysUserPageQuery;
import com.freeying.admin.sys.mapper.SysUserMapper;
import com.freeying.admin.sys.mapper.SysUserRoleMapper;
import com.freeying.admin.sys.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final PasswordEncoder passwordEncoder;

    public SysUserServiceImpl(SysUserMapper sysUserMapper,
                              SysUserRoleMapper sysUserRoleMapper,
                              PasswordEncoder passwordEncoder) {
        this.sysUserMapper = sysUserMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SysUserDTO info(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(sysUser, sysUserDTO, SysUserDTO.class);
        return sysUserDTO;
    }

    @Override
    public PageInfo<SysUserDTO> page(SysUserPageQuery qry) {
        return sysUserMapper.selectSysUserPage(qry.getPageQuery(), qry);
    }

    @Override
    public PageInfo<SysUserDTO> authUserPage(SysAuthUserPageQuery qry) {
        return sysUserMapper.selectAuthUserByRole(qry.getPageQuery(), qry);
    }

    @Override
    public PageInfo<SysUserDTO> unAuthUserPage(SysAuthUserPageQuery qry) {
        return sysUserMapper.selectUnAuthUserPage(qry.getPageQuery(), qry);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(SysUserCommand com) {

        SysUser dbUser = sysUserMapper.selectSysUserByUserName(StringUtils.trim(com.getUsername()));
        if (dbUser != null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "用户名已存在");
        }

        SysUser sysUser = toSysUser(com);
        int userInsert = sysUserMapper.insert(sysUser);

        int userRoleInsert = sysUserRoleMapper.insertUserRole(sysUser.getId(), com.getRoleIds());

        return DataCheck.insert(userInsert) && DataCheck.insert(userRoleInsert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysUserCommand com) {
        Long userId = Long.valueOf(com.getId());

        if (SystemConstants.ROLE_ADMIN.equals(com.getUsername().trim())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "超级管理员用户不可修改");
        }

        SysUser dbUser = sysUserMapper.selectById(userId);
        if (dbUser == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "用户id不存在");
        }

        if (!dbUser.getUsername().equals(com.getUsername())) {
            SysUser checkUser = sysUserMapper.selectSysUserByUserName(StringUtils.trim(com.getUsername()));
            if (checkUser != null) {
                throw new BadRequestException(HttpStatus.BAD_REQUEST, "用户名已存在");
            }
        }
        SysUser sysUser = toSysUser(com);
        sysUser.setId(userId);
        int userUpdate = sysUserMapper.updateById(sysUser);
        sysUserRoleMapper.deleteByUserId(userId);
        int userRoleInsert = sysUserRoleMapper.insertUserRole(sysUser.getId(), com.getRoleIds());
        return DataCheck.update(userUpdate) && DataCheck.insert(userRoleInsert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDel(IdCmdList ids) {
        List<Long> longIds = ids.getLongIds();
        int count = 0;
        for (Long id : longIds) {
            checkUser(id);
            count += sysUserMapper.deleteById(id);
        }
        return count == longIds.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPwd(IdCmd id) {
        Long longId = id.getLongId();
        checkUser(longId);
        int update = sysUserMapper.resetPassword(longId, defaultPassword());
        return DataCheck.update(update);
    }

    private SysUser toSysUser(SysUserCommand com) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(com.getUsername());
        sysUser.setNickname(com.getNickname());
        sysUser.setEmail(com.getEmail());
        sysUser.setPhoneNumber(com.getPhoneNumber());
        sysUser.setAvatar(com.getAvatar());
        sysUser.setDeptId(Long.valueOf(com.getDeptId()));
        sysUser.setPassword(defaultPassword());
        sysUser.setStatus(CommonConstants.STATUS_ENABLE);
        return sysUser;
    }

    @SuppressWarnings("squid:S6437")
    private String defaultPassword() {
        return passwordEncoder.encode(SystemConstants.DEFAULT_USER_PASSWORD);
    }

    private void checkUser(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);

        if (sysUser == null) {
            throw new ServiceException(String.format("%1$s不存在", id));
        }
        if (SystemConstants.ROLE_ADMIN.equals(sysUser.getUsername())) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "不允许操作超级管理员");
        }
    }

}
