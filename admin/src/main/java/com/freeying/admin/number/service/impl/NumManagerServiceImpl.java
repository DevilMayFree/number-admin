package com.freeying.admin.number.service.impl;

import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.admin.number.domain.po.NumManager;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.admin.number.mapper.NumManagerMapper;
import com.freeying.admin.number.service.NumManagerService;
import com.freeying.common.core.utils.DataConverter;
import com.freeying.common.core.web.PageInfo;
import org.springframework.stereotype.Service;

@Service
public class NumManagerServiceImpl implements NumManagerService {

    private final NumManagerMapper numManagerMapper;

    public NumManagerServiceImpl(NumManagerMapper numManagerMapper) {
        this.numManagerMapper = numManagerMapper;
    }

    @Override
    public PageInfo<NumManagerDTO> page(NumManagerPageQuery qry) {
        PageInfo<NumManager> pageInfo = numManagerMapper.selectNumManagerPage(qry.getPageQuery(), qry);
        return DataConverter.converter(numManager -> {
            NumManagerDTO dto = new NumManagerDTO();
            dto.setId(numManager.getId());
            dto.setNumber(numManager.getNumber());
            dto.setLabel(numManager.getLabel());
            dto.setCode(numManager.getCode());
            dto.setExpiryDate(numManager.getExpiryDate());
            dto.setRemainingDays(String.valueOf(numManager.getRemainingDays()));
            dto.setCardExpiryDate(numManager.getCardExpiryDate());
            dto.setCardRemainingDays(String.valueOf(numManager.getCardRemainingDays()));
            dto.setEntryDate(numManager.getEntryDate());
            dto.setRemark(numManager.getRemark());
            dto.setCreateBy(numManager.getCreateBy());
            dto.setGmtCreate(numManager.getGmtCreate());
            dto.setUpdateBy(numManager.getUpdateBy());
            dto.setGmtModified(numManager.getGmtModified());
            return dto;
        }, pageInfo);
    }

}
