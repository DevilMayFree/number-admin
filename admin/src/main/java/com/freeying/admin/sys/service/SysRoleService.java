package com.freeying.admin.sys.service;

import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysRoleCommand;
import com.freeying.admin.sys.domain.command.SysUserAuthCommand;
import com.freeying.admin.sys.domain.command.SysUserRoleCommand;
import com.freeying.admin.sys.domain.dto.SysRoleDTO;
import com.freeying.admin.sys.domain.query.SysRoleListQuery;
import com.freeying.admin.sys.domain.query.SysRolePageQuery;

import java.util.List;

public interface SysRoleService {

    PageInfo<SysRoleDTO> page(SysRolePageQuery qry);

    SysRoleDTO selectRoleById(Long roleId);

    List<SysRoleDTO> list(SysRoleListQuery qry);

    List<SysRoleDTO> selectRoleByUserId(Long userId);

    List<SysRoleDTO> selectRoleAllByFlag(Long userId);

    boolean add(SysRoleCommand com);

    boolean edit(SysRoleCommand com);

    boolean batchDel(IdCmdList ids);

    boolean authCancel(SysUserRoleCommand com);

    boolean userAuth(SysUserRoleCommand com);

    boolean roleAuth(SysUserAuthCommand com);
}
