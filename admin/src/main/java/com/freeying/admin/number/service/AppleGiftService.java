package com.freeying.admin.number.service;

import com.freeying.admin.number.domain.command.TgVIPAddBatchCommand;
import com.freeying.admin.number.domain.command.TgVIPTakeCommand;
import com.freeying.admin.number.domain.command.TgVIPUpdateTakeCommand;
import com.freeying.admin.number.domain.dto.TgVIPDTO;
import com.freeying.admin.number.domain.query.TgVIPLogPageQuery;
import com.freeying.admin.number.domain.query.TgVIPPageQuery;
import com.freeying.common.core.web.PageInfo;

import java.util.List;

public interface AppleGiftService {

    PageInfo<TgVIPDTO> page(TgVIPPageQuery qry);

    boolean addBatch(TgVIPAddBatchCommand com);

    List<TgVIPDTO> getTakeContent(TgVIPTakeCommand com);

    boolean updateTakeStatus(TgVIPUpdateTakeCommand com);

    PageInfo<TgVIPDTO> logPage(TgVIPLogPageQuery qry);
}
