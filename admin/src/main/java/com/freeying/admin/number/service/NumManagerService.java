package com.freeying.admin.number.service;

import com.freeying.admin.number.domain.command.NumManagerCommand;
import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.IdCmdList;

public interface NumManagerService {

    PageInfo<NumManagerDTO> page(NumManagerPageQuery qry);

    NumManagerDTO selectNumManagerById(Long id);

    boolean add(NumManagerCommand com);

    boolean edit(NumManagerCommand com);

    boolean batchDel(IdCmdList ids);
}
