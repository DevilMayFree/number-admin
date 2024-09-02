package com.freeying.admin.sys.service;

import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysDictTypeCommand;
import com.freeying.admin.sys.domain.dto.SysDictTypeDTO;
import com.freeying.admin.sys.domain.query.SysDictTypePageQuery;

import java.util.List;

public interface SysDictTypeService {

    PageInfo<SysDictTypeDTO> page(SysDictTypePageQuery qry);

    List<SysDictTypeDTO> all();

    SysDictTypeDTO selectDictTypeById(Long id);

    boolean add(SysDictTypeCommand com);

    boolean edit(SysDictTypeCommand com);

    boolean batchDel(IdCmdList ids);

    void refreshCache();
}
