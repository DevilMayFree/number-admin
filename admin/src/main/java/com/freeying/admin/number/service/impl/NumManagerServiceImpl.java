package com.freeying.admin.number.service.impl;

import com.freeying.admin.number.domain.command.NumAddBatchCommand;
import com.freeying.admin.number.domain.command.NumManagerCommand;
import com.freeying.admin.number.domain.command.UpdateRenewCommand;
import com.freeying.admin.number.domain.command.UpdateTeamCommand;
import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.admin.number.domain.po.NumManager;
import com.freeying.admin.number.domain.query.NumManagerExportQuery;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.admin.number.mapper.NumManagerMapper;
import com.freeying.admin.number.service.NumManagerService;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.common.core.exception.ServiceException;
import com.freeying.common.core.utils.DataConverter;
import com.freeying.common.core.utils.StringPool;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.DataCheck;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.framework.security.utils.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NumManagerServiceImpl implements NumManagerService {
    private static final Long DEFAULT_CARD_ADD_DAY = 30L;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeam(UpdateTeamCommand com) {
        List<String> ids = com.getIds();
        int count = 0;
        for (String id : ids) {
            NumManager dbNumber = numManagerMapper.selectById(id);
            if (dbNumber == null) {
                throw new ServiceException(String.format("%1$s不存在", id));
            }
            dbNumber.setLabel(com.getLabel());
            count += numManagerMapper.updateById(dbNumber);
        }
        return count == ids.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRenew(UpdateRenewCommand com) {
        List<String> ids = com.getIds();
        int count = 0;
        for (String id : ids) {
            NumManager dbNumber = numManagerMapper.selectById(id);
            if (dbNumber == null) {
                throw new ServiceException(String.format("%1$s不存在", id));
            }

            LocalDateTime nowTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

            String updateRemainingDays = com.getRemainingDays();
            if (StringUtils.isNotBlank(updateRemainingDays)) {
                long tempDbDay = 0L;
                String dbDay = dbNumber.getRemainingDays();
                if (StringUtils.isNotBlank(dbDay)) {
                    tempDbDay = Long.parseLong(dbDay);
                }
                long remainingDaysResult = Math.addExact(tempDbDay, Long.parseLong(updateRemainingDays));
                dbNumber.setRemainingDays(String.valueOf(remainingDaysResult));
                LocalDateTime newDateTime = nowTime.plusDays(remainingDaysResult);
                dbNumber.setExpiryDate(newDateTime);
            }

            String updateCardRemainingDays = com.getCardRemainingDays();
            if (StringUtils.isNotBlank(updateCardRemainingDays)) {
                long tempDbDay = 0L;
                String dbDay = dbNumber.getCardRemainingDays();
                if (StringUtils.isNotBlank(dbDay)) {
                    tempDbDay = Long.parseLong(dbDay);
                }
                long cardRemainingDaysResult = Math.addExact(tempDbDay, Long.parseLong(updateCardRemainingDays));
                dbNumber.setCardRemainingDays(String.valueOf(cardRemainingDaysResult));
                LocalDateTime newDateTime = nowTime.plusDays(cardRemainingDaysResult);
                dbNumber.setCardExpiryDate(newDateTime);
            }

            count += numManagerMapper.updateById(dbNumber);
        }
        return count == ids.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addBatch(NumAddBatchCommand com) {
        List<String> numList = com.getNumList();
        if (CollectionUtils.isEmpty(numList)) {
            return false;
        }
        Long userId = SecurityUtil.getCurrentUserId();
        String firstCode = com.getFirstCode();
        String[] codeInput = extractNumbers(firstCode);
        String codeRaw = codeInput[0];
        long first = Long.parseLong(codeInput[1]);
        first--;

        List<NumManager> poList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cardExpiryDate = now.plusDays(DEFAULT_CARD_ADD_DAY);

        for (String num : numList) {
            String newCode = codeRaw + (first + 1L);
            NumManager po = new NumManager();
            po.setCode(newCode);
            po.setNumber(num.trim());
            po.setEntryDate(now);
            po.setRemainingDays(StringPool.EMPTY);
            po.setCardRemainingDays(String.valueOf(DEFAULT_CARD_ADD_DAY));
            po.setCardExpiryDate(cardExpiryDate);
            po.setDeleted(0);
            po.setVersion(0L);
            po.setCreateBy(userId);
            po.setGmtCreate(now);
            poList.add(po);
            first++;
        }
        int insert = numManagerMapper.insertBatchSomeColumn(poList);
        return DataCheck.insert(insert);
    }

    private String[] extractNumbers(String input) {
        // 定义正则表达式，匹配前面是字母，后面是数字的模式
        Pattern pattern = Pattern.compile("^([a-zA-Z]+)(\\d+)$");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            // 提取字母部分和数字部分
            String letters = matcher.group(1);
            String numbers = matcher.group(2);
            return new String[]{letters, numbers};
        } else {
            throw new IllegalArgumentException("输入格式不正确，请确保前面是字母，后面是数字");
        }
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

    private void checkUpdateNumber(String id) {
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
        LocalDateTime entryDate = LocalDateTime.now();
        po.setEntryDate(entryDate);

        String remainingDays = com.getRemainingDays();
        if (StringUtils.isBlank(remainingDays)) {
            po.setRemainingDays(null);
            po.setExpiryDate(null);
        } else {
            po.setRemainingDays(remainingDays);
            LocalDateTime expiryDate = entryDate.plusDays(Long.parseLong(remainingDays));
            po.setExpiryDate(expiryDate);
        }
        String cardRemainingDays = com.getCardRemainingDays();
        if (StringUtils.isBlank(cardRemainingDays)) {
            po.setCardRemainingDays(null);
            po.setCardExpiryDate(null);
        } else {
            po.setCardRemainingDays(cardRemainingDays);
            LocalDateTime cardExpiryDate = entryDate.plusDays(Long.parseLong(cardRemainingDays));
            po.setCardExpiryDate(cardExpiryDate);
        }
        po.setRemark(com.getRemark());
        return po;
    }
}
