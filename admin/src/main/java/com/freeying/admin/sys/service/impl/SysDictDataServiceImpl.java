package com.freeying.admin.sys.service.impl;

import com.freeying.common.core.constant.CacheConstants;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.common.core.exception.ServiceException;
import com.freeying.common.core.utils.DataConverter;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.cache.annotation.AppCacheable;
import com.freeying.framework.cache.service.RedisService;
import com.freeying.framework.data.core.DataCheck;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysDictDataCommand;
import com.freeying.admin.sys.domain.dto.SysDictDataDTO;
import com.freeying.admin.sys.domain.po.SysDictData;
import com.freeying.admin.sys.domain.po.SysDictType;
import com.freeying.admin.sys.domain.query.SysDictDataPageQuery;
import com.freeying.admin.sys.domain.query.SysDictDataQuery;
import com.freeying.admin.sys.mapper.SysDictDataMapper;
import com.freeying.admin.sys.mapper.SysDictTypeMapper;
import com.freeying.admin.sys.service.SysDictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SysDictDataServiceImpl implements SysDictDataService {

    private final SysDictDataMapper sysDictDataMapper;
    private final SysDictTypeMapper sysDictTypeMapper;
    private final RedisService redisService;

    public SysDictDataServiceImpl(SysDictDataMapper sysDictDataMapper,
                                  SysDictTypeMapper sysDictTypeMapper,
                                  RedisService redisService) {
        this.sysDictDataMapper = sysDictDataMapper;
        this.sysDictTypeMapper = sysDictTypeMapper;
        this.redisService = redisService;
    }

    @Override
    public PageInfo<SysDictDataDTO> page(SysDictDataPageQuery qry) {
        PageInfo<SysDictData> pageInfo = sysDictDataMapper.selectSysDictDataPage(qry.getPageQuery(), qry);

        return DataConverter.converter(sysDictData -> {
            SysDictDataDTO dto = new SysDictDataDTO();
            BeanUtils.copyProperties(sysDictData, dto);
            return dto;
        }, pageInfo);
    }

    @Override
    @AppCacheable(value = CacheConstants.SYS_DICT_DATA, key = "#qry.code")
    public List<SysDictDataDTO> detail(SysDictDataQuery qry) {
        return sysDictDataMapper.selectDictDataByCode(qry);
    }

    @Override
    public SysDictDataDTO selectDictDataById(Long id) {
        SysDictData sysDictData = sysDictDataMapper.selectById(id);
        SysDictDataDTO dto = new SysDictDataDTO();
        if (sysDictData != null) {
            BeanUtils.copyProperties(sysDictData, dto);
        }
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(SysDictDataCommand com) {
        SysDictData db = sysDictDataMapper.selectDictData(
                Long.valueOf(com.getDictId()),
                StringUtils.trim(com.getLabel()),
                StringUtils.trim(com.getValue()));
        if (db != null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "字典数据文本/字典数据值 已存在");
        }
        SysDictData sysDictData = toSysDictData(com);
        int insert = sysDictDataMapper.insert(sysDictData);
        boolean result = DataCheck.insert(insert);
        if (result) {
            refreshCache(Long.valueOf(com.getDictId()));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysDictDataCommand com) {

        SysDictData oldDbData = sysDictDataMapper.selectById(com.getId());
        if (oldDbData == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "字典数据不存在");
        }
        if (!oldDbData.getLabel().equals(com.getLabel())
                || !oldDbData.getValue().equals(com.getValue())) {
            SysDictData db = sysDictDataMapper.selectDictData(
                    Long.valueOf(com.getDictId()),
                    StringUtils.trim(com.getLabel()),
                    StringUtils.trim(com.getValue()));
            if (db != null && !Objects.equals(db.getId(), Long.valueOf(com.getId()))) {
                throw new BadRequestException(HttpStatus.BAD_REQUEST, "字典数据文本/字典数据值 已存在");
            }
        }

        SysDictData sysDictData = toSysDictData(com);
        sysDictData.setId(Long.valueOf(com.getId()));
        int update = sysDictDataMapper.updateById(sysDictData);
        boolean result = DataCheck.update(update);
        if (result) {
            refreshCache(Long.valueOf(com.getDictId()));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDel(IdCmdList ids) {
        List<Long> longIds = ids.getLongIds();
        int count = 0;
        for (Long id : longIds) {
            SysDictData sysDictData = sysDictDataMapper.selectById(id);
            if (sysDictData == null) {
                throw new ServiceException(String.format("%1$s不存在", id));
            }
            count += sysDictDataMapper.deleteById(id);
            refreshCache(sysDictData.getDictId());
        }
        return count == longIds.size();
    }

    private void refreshCache(Long dictId) {
        SysDictType sysDictType = sysDictTypeMapper.selectById(dictId);
        redisService.del(CacheConstants.SYS_DICT_DATA_KEY + sysDictType.getCode());
    }

    private SysDictData toSysDictData(SysDictDataCommand com) {
        SysDictData sysDictData = new SysDictData();
        BeanUtils.copyProperties(com, sysDictData);
        sysDictData.setDictId(Long.valueOf(com.getDictId()));
        return sysDictData;
    }
}
