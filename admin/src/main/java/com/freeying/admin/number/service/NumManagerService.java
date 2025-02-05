package com.freeying.admin.number.service;

import com.freeying.admin.number.domain.command.*;
import com.freeying.admin.number.domain.dto.DoEditBatchDTO;
import com.freeying.admin.number.domain.dto.EditBatchDTO;
import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.admin.number.domain.query.NumManagerExportQuery;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.IdCmdList;

import java.util.List;

public interface NumManagerService {

    PageInfo<NumManagerDTO> page(NumManagerPageQuery qry);

    List<NumManagerDTO> export(NumManagerExportQuery qry);

    NumManagerDTO selectNumManagerById(Long id);

    boolean add(NumManagerCommand com);

    boolean edit(NumManagerCommand com);

    boolean batchDel(IdCmdList ids);

    boolean updateTeam(UpdateTeamCommand com);

    boolean updateRenew(UpdateRenewCommand com);

    boolean addBatch(NumAddBatchCommand com);

    EditBatchDTO editBatch(NumEditBatchCommand com);

    EditBatchDTO editCardBatch(NumEditBatchCommand com);

    DoEditBatchDTO doEditBatch(NumEditBatchCommand com);

    DoEditBatchDTO doEditCardBatch(NumEditBatchCommand com);
}
