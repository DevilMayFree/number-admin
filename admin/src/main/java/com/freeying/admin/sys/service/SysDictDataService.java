package com.freeying.admin.sys.service;

import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysDictDataCommand;
import com.freeying.admin.sys.domain.dto.SysDictDataDTO;
import com.freeying.admin.sys.domain.query.SysDictDataPageQuery;
import com.freeying.admin.sys.domain.query.SysDictDataQuery;

import java.util.List;

public interface SysDictDataService {

    PageInfo<SysDictDataDTO> page(SysDictDataPageQuery qry);

    List<SysDictDataDTO> detail(SysDictDataQuery qry);

    SysDictDataDTO selectDictDataById(Long id);

    boolean add(SysDictDataCommand com);

    boolean edit(SysDictDataCommand com);

    boolean batchDel(IdCmdList ids);
}
