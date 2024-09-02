package com.freeying.admin.sys.mapper;

import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.PlusBaseMapper;
import com.freeying.admin.sys.domain.po.SysRole;
import com.freeying.admin.sys.domain.query.SysRolePageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends PlusBaseMapper<SysRole> {

    /**
     * 系统角色分页查询
     *
     * @param pageInfo 分页对象
     * @param qry      SysRolePageQuery
     * @return 分页对象
     */
    PageInfo<SysRole> selectSysRolePage(@Param("page") PageInfo<?> pageInfo, @Param("qry") SysRolePageQuery qry);

    /**
     * 系统角色列表全部
     *
     * @return 列表对象
     */
    List<SysRole> selectSysRoleListAll();

    /**
     * 根据用户id查询用户角色
     *
     * @param userId 用户id
     * @return 用户角色列表
     */
    List<SysRole> selectSysRoleByUserId(@Param("userId") Long userId);

    /**
     * 根据用户id查询用户角色编码
     *
     * @param userId 用户id
     * @return 用户角色列表
     */
    List<String> selectRoleCodeByUserId(@Param("userId") Long userId);

    /**
     * 根据角色名称查询角色
     *
     * @param name 角色名称
     * @return 角色
     */
    SysRole selectRoleByName(@Param("name") String name);

    /**
     * 根据角色编码查询角色
     *
     * @param code 角色编码
     * @return 角色
     */
    SysRole selectRoleByCode(@Param("code") String code);

    /**
     * 查询角色关联用户数量
     *
     * @param roleId 角色id
     * @return 关联数量
     */
    Integer countUserRoleByRoleId(@Param("roleId") Long roleId);
}
