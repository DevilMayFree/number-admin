package com.freeying.admin.sys.mapper;

import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.PlusBaseMapper;
import com.freeying.admin.sys.domain.dto.SysDictDataDTO;
import com.freeying.admin.sys.domain.po.SysDictData;
import com.freeying.admin.sys.domain.query.SysDictDataPageQuery;
import com.freeying.admin.sys.domain.query.SysDictDataQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDictDataMapper extends PlusBaseMapper<SysDictData> {

    /**
     * 系统用户分页查询
     *
     * @param pageInfo 分页对象
     * @param qry      SysUserPageQuery
     * @return 分页对象
     */
    PageInfo<SysDictData> selectSysDictDataPage(@Param("page") PageInfo<?> pageInfo, @Param("qry") SysDictDataPageQuery qry);

    /**
     * 根据字典类型编码查询字典数据列表
     *
     * @param qry SysDictDataQuery
     * @return 字典数据列表
     */
    List<SysDictDataDTO> selectDictDataByCode(@Param("qry") SysDictDataQuery qry);

    /**
     * 查询对应字典类型id下的字典数据数量
     *
     * @param dictId 字典id
     * @return 字典数据数量
     */
    Integer countDictDataByDictId(@Param("dictId") Long dictId);

    /**
     * 查询字典项文本和字典项值是否已经存在
     *
     * @param dictId 字典类型id
     * @param label  字典项文本
     * @param value  字典项值
     * @return 字典项
     */
    SysDictData selectDictData(@Param("dictId") Long dictId, @Param("label") String label, @Param("value") String value);
}
