package com.freeying.admin.sys.service.impl;

import com.freeying.common.core.constant.CacheConstants;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.common.core.exception.ServiceException;
import com.freeying.common.core.utils.DataConverter;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.cache.service.RedisService;
import com.freeying.framework.data.core.DataCheck;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysDictTypeCommand;
import com.freeying.admin.sys.domain.dto.SysDictTypeDTO;
import com.freeying.admin.sys.domain.po.SysDictType;
import com.freeying.admin.sys.domain.query.SysDictTypePageQuery;
import com.freeying.admin.sys.mapper.SysDictDataMapper;
import com.freeying.admin.sys.mapper.SysDictTypeMapper;
import com.freeying.admin.sys.service.SysDictTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class SysDictTypeServiceImpl implements SysDictTypeService {

    private final SysDictTypeMapper sysDictTypeMapper;
    private final SysDictDataMapper sysDictDataMapper;
    private final RedisService redisService;

    public SysDictTypeServiceImpl(SysDictTypeMapper sysDictTypeMapper,
                                  SysDictDataMapper sysDictDataMapper,
                                  RedisService redisService) {
        this.sysDictTypeMapper = sysDictTypeMapper;
        this.sysDictDataMapper = sysDictDataMapper;
        this.redisService = redisService;
    }

    @Override
    public PageInfo<SysDictTypeDTO> page(SysDictTypePageQuery qry) {
        PageInfo<SysDictType> pageInfo = sysDictTypeMapper.selectSysDictTypePage(qry.getPageQuery(), qry);

        return DataConverter.converter(sysDictType -> {
            SysDictTypeDTO dto = new SysDictTypeDTO();
            BeanUtils.copyProperties(sysDictType, dto);
            return dto;
        }, pageInfo);
    }

    @Override
    public List<SysDictTypeDTO> all() {
        return sysDictTypeMapper.selectSysDictTypeAll();
    }

    @Override
    public SysDictTypeDTO selectDictTypeById(Long id) {
        SysDictType sysDictType = sysDictTypeMapper.selectById(id);
        SysDictTypeDTO dto = new SysDictTypeDTO();
        if (sysDictType != null) {
            BeanUtils.copyProperties(sysDictType, dto);
        }
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(SysDictTypeCommand com) {

        checkDictTypeCode(com);
        SysDictType sysDictType = toSysDictType(com);
        int insert = sysDictTypeMapper.insert(sysDictType);

        return DataCheck.insert(insert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysDictTypeCommand com) {
        SysDictType oldDbDict = sysDictTypeMapper.selectById(com.getId());
        if (oldDbDict == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "字典id不存在");
        }
        if (!oldDbDict.getCode().equals(com.getCode())) {
            checkDictTypeCode(com);
        }

        SysDictType sysDictType = toSysDictType(com);
        sysDictType.setId(Long.valueOf(com.getId()));
        int update = sysDictTypeMapper.updateById(sysDictType);
        return DataCheck.update(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDel(IdCmdList ids) {
        List<Long> longIds = ids.getLongIds();
        int count = 0;
        for (Long id : longIds) {
            SysDictType sysDictType = sysDictTypeMapper.selectById(id);
            if (sysDictType == null) {
                throw new ServiceException(String.format("%1$s不存在", id));
            }
            if (sysDictDataMapper.countDictDataByDictId(sysDictType.getId()) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", sysDictType.getName()));
            }
            count += sysDictTypeMapper.deleteById(id);
            redisService.del(CacheConstants.SYS_DICT_DATA_KEY + sysDictType.getCode());
        }
        return count == longIds.size();
    }

    @Override
    public void refreshCache() {
        Set<String> keys = redisService.keys(CacheConstants.SYS_DICT_DATA_KEY + "*");
        redisService.del(keys);
    }

    /**
     * 校验字典编码
     *
     * @param com SysDictTypeCommand
     */
    private void checkDictTypeCode(SysDictTypeCommand com) {
        SysDictType dbDict = sysDictTypeMapper.selectSysDictTypeByCode(StringUtils.trim(com.getCode()));
        if (dbDict != null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "字典编码已存在");
        }
    }

    private SysDictType toSysDictType(SysDictTypeCommand com) {
        SysDictType sysDictType = new SysDictType();
        BeanUtils.copyProperties(com, sysDictType);
        return sysDictType;
    }

}
