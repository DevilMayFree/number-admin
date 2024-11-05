package com.freeying.admin.number.service.impl;

import com.freeying.admin.number.domain.command.TgVIPAddBatchCommand;
import com.freeying.admin.number.domain.command.TgVIPTakeCommand;
import com.freeying.admin.number.domain.command.TgVIPUpdateTakeCommand;
import com.freeying.admin.number.domain.dto.TgVIPDTO;
import com.freeying.admin.number.domain.po.GmailVIP;
import com.freeying.admin.number.domain.query.TgVIPLogPageQuery;
import com.freeying.admin.number.domain.query.TgVIPPageQuery;
import com.freeying.admin.number.mapper.GmailVIPMapper;
import com.freeying.admin.number.service.GmailVIPService;
import com.freeying.common.core.enums.ContentStatusEnum;
import com.freeying.common.core.exception.ServiceException;
import com.freeying.common.core.utils.DataConverter;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.BasePO;
import com.freeying.framework.data.core.DataCheck;
import com.freeying.framework.security.utils.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GmailVIPServiceImpl implements GmailVIPService {

    private final GmailVIPMapper gmailVIPMapper;

    public GmailVIPServiceImpl(GmailVIPMapper gmailVIPMapper) {
        this.gmailVIPMapper = gmailVIPMapper;
    }

    @Override
    public PageInfo<TgVIPDTO> page(TgVIPPageQuery qry) {
        PageInfo<GmailVIP> tgVIPPageInfo = gmailVIPMapper.selectGmailVIPPage(qry.getPageQuery(), qry);
        return DataConverter.converter(GmailVIPServiceImpl::toDto, tgVIPPageInfo);
    }

    @Override
    public boolean addBatch(TgVIPAddBatchCommand com) {
        List<String> contentList = com.getContentList();
        if (CollectionUtils.isEmpty(contentList)) {
            return false;
        }
        Long userId = SecurityUtil.getCurrentUserId();
        LocalDateTime now = LocalDateTime.now();

        List<GmailVIP> poList = new ArrayList<>();
        for (String content : contentList) {
            if (StringUtils.isBlank(content)) {
                continue;
            }
            GmailVIP po = new GmailVIP();
            po.setContent(content);
            po.setStatus(ContentStatusEnum.NONE.getValue());
            po.setDeleted(0);
            po.setVersion(0L);
            po.setCreateBy(userId);
            po.setGmtCreate(now);
            poList.add(po);
        }
        int insert = gmailVIPMapper.insertBatchSomeColumn(poList);
        return DataCheck.insert(insert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized List<TgVIPDTO> getTakeContent(TgVIPTakeCommand com) {
        Integer count = com.getCount();
        if (count == null) {
            throw new IllegalArgumentException("参数错误");
        }
        List<GmailVIP> takeList = gmailVIPMapper.getTakeContent(count);
        if (CollectionUtils.isEmpty(takeList)) {
            throw new IllegalArgumentException("暂无可领取的号码");
        }

        List<Long> ids = takeList.stream().map(BasePO::getId).toList();
        Integer updateCount = gmailVIPMapper.updateStatus(ContentStatusEnum.DO.getValue(), ids);
        if (updateCount != takeList.size()) {
            throw new ServiceException("更新状态失败");
        }
        return DataConverter.converter(GmailVIPServiceImpl::toDto, takeList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean updateTakeStatus(TgVIPUpdateTakeCommand com) {
        List<String> ids = com.getIds();
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        Integer status = com.getStatus();
        if (ContentStatusEnum.getEnum(status) == null) {
            return false;
        }
        List<Long> idList;
        try {
            idList = ids.stream().map(Long::valueOf).toList();
        } catch (Exception e) {
            return false;
        }
        // 取消,返回成未领取状态
        if (ContentStatusEnum.NONE.getValue().equals(status)) {
            Integer updateCount = gmailVIPMapper.updateStatus(status, idList);
            return DataCheck.batch(updateCount, ids.size());
        } else if (ContentStatusEnum.DONE.getValue().equals(status)) {
            // 确定领取
            List<GmailVIP> poList = gmailVIPMapper.selectBatchIds(idList);
            Long userId = SecurityUtil.getCurrentUserId();
            LocalDateTime now = LocalDateTime.now();
            int count = 0;
            for (GmailVIP po : poList) {
                po.setStatus(status);
                po.setTakeTime(now);
                po.setTakeUserId(userId);
                po.setUpdateBy(userId);
                po.setGmtModified(now);
                count += gmailVIPMapper.updateById(po);
            }
            return DataCheck.batch(count, idList.size());
        }
        return false;
    }

    @Override
    public PageInfo<TgVIPDTO> logPage(TgVIPLogPageQuery qry) {
        Long userId = SecurityUtil.getCurrentUserId();
        PageInfo<GmailVIP> tgVIPPageInfo = gmailVIPMapper.selectGmailVIPLogPage(qry.getPageQuery(), qry, userId);
        return DataConverter.converter(GmailVIPServiceImpl::toDto, tgVIPPageInfo);
    }

    private static TgVIPDTO toDto(GmailVIP po) {
        TgVIPDTO dto = new TgVIPDTO();
        dto.setId(po.getId());
        dto.setContent(po.getContent());
        dto.setTakeTime(po.getTakeTime());
        dto.setUpdateBy(po.getUpdateBy());
        dto.setCreateBy(po.getCreateBy());
        dto.setGmtCreate(po.getGmtCreate());
        dto.setGmtModified(po.getGmtModified());
        return dto;
    }
}
