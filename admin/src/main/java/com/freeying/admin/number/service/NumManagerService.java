package com.freeying.admin.number.service;

import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.common.core.web.PageInfo;

public interface NumManagerService {

    PageInfo<NumManagerDTO> page(NumManagerPageQuery qry);

}
