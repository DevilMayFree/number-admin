package com.freeying.admin.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {

    /**
     * 新增用户与角色关联
     *
     * @param userId  用户id
     * @param roleIds 角色id列表
     * @return Integer
     */
    Integer insertUserRole(@Param("userId") Long userId, @Param("roleIds") List<String> roleIds);

    /**
     * 删除用户与角色关联
     *
     * @param roleId  角色id
     * @param userIds 用户id列表
     * @return Integer
     */
    Integer deleteByUserRole(@Param("roleId") Long roleId, @Param("userIds") List<String> userIds);

    /**
     * 批量用户授权
     *
     * @param roleId  角色id
     * @param userIds 用户id列表
     * @return Integer
     */
    Integer authUserRole(@Param("roleId") Long roleId, @Param("userIds") List<String> userIds);

    /**
     * 删除用户与角色关联
     *
     * @param userId 用户id
     * @return Integer
     */
    Integer deleteByUserId(@Param("userId") Long userId);
}
