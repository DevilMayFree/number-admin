package com.freeying.admin.sys.mapper;

import com.freeying.framework.data.core.PlusBaseMapper;
import com.freeying.admin.sys.domain.dto.SysDeptDTO;
import com.freeying.admin.sys.domain.po.SysDept;
import com.freeying.admin.sys.domain.query.SysDeptListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptMapper extends PlusBaseMapper<SysDept> {

    /**
     * 系统部门列表查询
     *
     * @param qry SysDeptListQuery
     * @return 列表对象
     */
    List<SysDeptDTO> selectSysDeptList(@Param("qry") SysDeptListQuery qry);

    /**
     * 根据部门名称查询部门数据
     *
     * @param name 部门名称
     * @return 部门数据
     */
    SysDept selectSysDictByName(@Param("name") String name);

    /**
     * 根据pid查询当前id下的所有子部门数据
     *
     * @param pid 父级id
     * @return 列表对象
     */
    List<SysDeptDTO> selectSysDeptListByPid(@Param("pid") Long pid);
}
