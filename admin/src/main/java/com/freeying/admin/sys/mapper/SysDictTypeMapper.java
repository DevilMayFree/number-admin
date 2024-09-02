package com.freeying.admin.sys.mapper;

import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.PlusBaseMapper;
import com.freeying.admin.sys.domain.dto.SysDictTypeDTO;
import com.freeying.admin.sys.domain.po.SysDictType;
import com.freeying.admin.sys.domain.query.SysDictTypePageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDictTypeMapper extends PlusBaseMapper<SysDictType> {

    /**
     * 系统字典类型分页查询
     *
     * @param pageInfo 分页对象
     * @param qry      SysDictTypePageQuery
     * @return 分页对象
     */
    PageInfo<SysDictType> selectSysDictTypePage(@Param("page") PageInfo<?> pageInfo, @Param("qry") SysDictTypePageQuery qry);

    /**
     * 查询所有系统字典
     *
     * @return 系统字典列表
     */
    List<SysDictTypeDTO> selectSysDictTypeAll();

    /**
     * 根据字典编码查询字典
     *
     * @param code 字典编码
     * @return 字典
     */
    SysDictType selectSysDictTypeByCode(@Param("code") String code);
}
