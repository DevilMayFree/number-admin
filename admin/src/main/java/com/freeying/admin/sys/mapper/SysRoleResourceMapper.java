package com.freeying.admin.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleResourceMapper {

    /**
     * 新增用户与角色关联
     *
     * @param roleId      角色id
     * @param resourceIds 资源id列表
     * @return Integer
     */
    Integer insertRoleResource(@Param("roleId") Long roleId, @Param("resourceIds") List<String> resourceIds);

}
