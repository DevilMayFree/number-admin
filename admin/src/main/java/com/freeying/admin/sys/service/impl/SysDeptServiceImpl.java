package com.freeying.admin.sys.service.impl;

import com.freeying.common.core.exception.BadRequestException;
import com.freeying.framework.data.core.DataCheck;
import com.freeying.framework.data.core.IdCmd;
import com.freeying.admin.sys.domain.command.SysDeptCommand;
import com.freeying.admin.sys.domain.dto.SysDeptDTO;
import com.freeying.admin.sys.domain.po.SysDept;
import com.freeying.admin.sys.domain.query.SysDeptListQuery;
import com.freeying.admin.sys.mapper.SysDeptMapper;
import com.freeying.admin.sys.service.SysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;

    public SysDeptServiceImpl(SysDeptMapper sysDeptMapper) {
        this.sysDeptMapper = sysDeptMapper;
    }

    @Override
    public List<SysDeptDTO> list(SysDeptListQuery qry) {
        return sysDeptMapper.selectSysDeptList(qry);
    }

    @Override
    public SysDeptDTO selectDeptById(Long id) {
        SysDept sysDept = sysDeptMapper.selectById(id);
        SysDeptDTO dto = new SysDeptDTO();
        if (sysDept != null) {
            BeanUtils.copyProperties(sysDept, dto);
        }
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(SysDeptCommand com) {
        SysDept db = sysDeptMapper.selectSysDictByName(com.getName());
        if (db != null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "部门已存在");
        }

        SysDept sysDept = toSysDept(com);
        int insert = sysDeptMapper.insert(sysDept);
        return DataCheck.insert(insert);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysDeptCommand com) {
        SysDept db = sysDeptMapper.selectById(com.getId());
        if (db == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "部门不存在");
        }

        if (!db.getName().equals(com.getName())) {
            SysDept oldData = sysDeptMapper.selectSysDictByName(com.getName());
            if (oldData != null) {
                throw new BadRequestException(HttpStatus.BAD_REQUEST, "部门已存在");
            }
        }
        SysDept sysDept = toSysDept(com);
        sysDept.setId(Long.valueOf(com.getId()));
        int update = sysDeptMapper.updateById(sysDept);

        return DataCheck.update(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean del(IdCmd id) {
        int del = sysDeptMapper.deleteById(id.getId());
        return DataCheck.delete(del);
    }

    private SysDept toSysDept(SysDeptCommand com) {
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(com, sysDept);
        sysDept.setPid(Long.valueOf(com.getPid()));
        return sysDept;
    }

}
