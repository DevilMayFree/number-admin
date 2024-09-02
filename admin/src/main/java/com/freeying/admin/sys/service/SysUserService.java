package com.freeying.admin.sys.service;

import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.IdCmd;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysUserCommand;
import com.freeying.admin.sys.domain.dto.SysUserDTO;
import com.freeying.admin.sys.domain.query.SysAuthUserPageQuery;
import com.freeying.admin.sys.domain.query.SysUserPageQuery;

public interface SysUserService {

    SysUserDTO info(Long userId);

    PageInfo<SysUserDTO> page(SysUserPageQuery qry);

    PageInfo<SysUserDTO> authUserPage(SysAuthUserPageQuery qry);

    PageInfo<SysUserDTO> unAuthUserPage(SysAuthUserPageQuery qry);

    boolean add(SysUserCommand com);

    boolean edit(SysUserCommand com);

    boolean batchDel(IdCmdList ids);

    boolean resetPwd(IdCmd id);
}
