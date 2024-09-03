package com.freeying.admin.number.service.impl;

import com.freeying.admin.number.domain.command.NumManagerCommand;
import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.admin.number.domain.po.NumManager;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.admin.number.mapper.NumManagerMapper;
import com.freeying.admin.number.service.NumManagerService;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.common.core.utils.DataConverter;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.DataCheck;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NumManagerServiceImpl implements NumManagerService {

    private final NumManagerMapper numManagerMapper;

    public NumManagerServiceImpl(NumManagerMapper numManagerMapper) {
        this.numManagerMapper = numManagerMapper;
    }

    @Override
    public PageInfo<NumManagerDTO> page(NumManagerPageQuery qry) {
        PageInfo<NumManager> pageInfo = numManagerMapper.selectNumManagerPage(qry.getPageQuery(), qry);
        return DataConverter.converter(NumManagerServiceImpl::poToDTO, pageInfo);
    }

    @Override
    public NumManagerDTO selectNumManagerById(Long id) {
        NumManager po = numManagerMapper.selectById(id);
        return poToDTO(po);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(NumManagerCommand com) {
        checkNumber(com.getNumber());
        checkCode(com.getCode());
        NumManager po = comToPO(com);
        int insert = numManagerMapper.insert(po);
        return DataCheck.insert(insert);
    }

    private void checkNumber(String number) {
        NumManager db = numManagerMapper.selectNumManagerByNumber(number);
        if (db != null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, String.format("新增号码, 该号码%s已存在", number));
        }
    }

    private void checkCode(String code) {
        NumManager db = numManagerMapper.selectNumManagerByCode(code);
        if (db != null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, String.format("新增号码, 该编码%s已存在", code));
        }
    }

    private static NumManagerDTO poToDTO(NumManager numManager) {
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
    }

    private static NumManager comToPO(NumManagerCommand com) {
        NumManager po = new NumManager();
        po.setNumber(com.getNumber());
        po.setLabel(com.getLabel());
        po.setCode(com.getCode());
        po.setExpiryDate(com.getExpiryDate());
        po.setRemainingDays(Long.valueOf(com.getRemainingDays()));
        po.setCardExpiryDate(com.getCardExpiryDate());
        po.setCardRemainingDays(Long.valueOf(com.getCardRemainingDays()));
        po.setEntryDate(com.getEntryDate());
        po.setRemark(com.getRemark());
        return po;
    }
}
