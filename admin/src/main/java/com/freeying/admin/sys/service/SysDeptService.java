package com.freeying.admin.sys.service;

import com.freeying.framework.data.core.IdCmd;
import com.freeying.admin.sys.domain.command.SysDeptCommand;
import com.freeying.admin.sys.domain.dto.SysDeptDTO;
import com.freeying.admin.sys.domain.query.SysDeptListQuery;

import java.util.List;

public interface SysDeptService {

    List<SysDeptDTO> list(SysDeptListQuery qry);

    SysDeptDTO selectDeptById(Long id);

    boolean add(SysDeptCommand com);

    boolean edit(SysDeptCommand com);

    boolean del(IdCmd id);
}
