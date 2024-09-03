package com.freeying.admin.number.service.impl;

import com.freeying.admin.number.domain.command.NumManagerCommand;
import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.admin.number.domain.po.NumManager;
import com.freeying.admin.number.domain.query.NumManagerExportQuery;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.admin.number.mapper.NumManagerMapper;
import com.freeying.admin.number.service.NumManagerService;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.common.core.exception.ServiceException;
import com.freeying.common.core.utils.DataConverter;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.DataCheck;
import com.freeying.framework.data.core.IdCmdList;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<NumManagerDTO> export(NumManagerExportQuery qry) {
        List<NumManager> list = numManagerMapper.selectNumManagerExport(qry);
        return DataConverter.converter(NumManagerServiceImpl::poToDTO, list);
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(NumManagerCommand com) {
        checkUpdateNumber(com);
        NumManager po = comToPO(com);
        po.setId(Long.valueOf(com.getId()));
        int update = numManagerMapper.updateById(po);
        return DataCheck.update(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDel(IdCmdList ids) {
        List<Long> longIds = ids.getLongIds();
        int count = 0;
        for (Long id : longIds) {
            checkDelNumber(id);
            count += numManagerMapper.deleteById(id);
        }
        return count == longIds.size();
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

    private void checkUpdateNumber(NumManagerCommand com) {
        NumManager db = numManagerMapper.selectById(com.getId());

        if (db == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "号码id不存在");
        }

        if (!db.getNumber().equals(com.getNumber())) {
            checkNumber(com.getNumber());
        }

        if (!db.getCode().equals(com.getCode())) {
            checkCode(com.getCode());
        }
    }

    private void checkDelNumber(Long id) {
        NumManager dbNumber = numManagerMapper.selectById(id);

        if (dbNumber == null) {
            throw new ServiceException(String.format("%1$s不存在", id));
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
        po.setCardExpiryDate(com.getCardExpiryDate());
        po.setRemark(com.getRemark());

        LocalDateTime entryDate = LocalDateTime.now();
        po.setEntryDate(entryDate);
        LocalDateTime expiryDate = entryDate.plusDays(Long.parseLong(com.getRemainingDays()));
        po.setExpiryDate(expiryDate);
        LocalDateTime cardExpiryDate = entryDate.plusDays(Long.parseLong(com.getCardRemainingDays()));
        po.setCardExpiryDate(cardExpiryDate);

        return po;
    }
}
