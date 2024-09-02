package com.freeying.admin.sys.mapper;

import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.PlusBaseMapper;
import com.freeying.admin.sys.domain.dto.SysUserDTO;
import com.freeying.admin.sys.domain.po.SysUser;
import com.freeying.admin.sys.domain.query.SysAuthUserPageQuery;
import com.freeying.admin.sys.domain.query.SysUserPageQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends PlusBaseMapper<SysUser> {

    /**
     * 根据用户名查询系统用户
     *
     * @param username 用户名
     * @return SysUser
     */
    SysUser selectSysUserByUserName(@Param("username") String username);

    /**
     * 系统用户分页查询
     *
     * @param pageInfo 分页对象
     * @param qry      SysUserPageQuery
     * @return 分页对象
     */
    PageInfo<SysUserDTO> selectSysUserPage(@Param("page") PageInfo<?> pageInfo, @Param("qry") SysUserPageQuery qry);

    /**
     * 查询已分配该角色的用户
     *
     * @param pageInfo 分页对象
     * @param qry      SysUserPageQuery
     * @return 分页对象
     */
    PageInfo<SysUserDTO> selectAuthUserByRole(@Param("page") PageInfo<?> pageInfo, @Param("qry") SysAuthUserPageQuery qry);

    /**
     * 查询未分配到该角色的用户
     *
     * @param pageInfo 分页对象
     * @param qry      SysUserPageQuery
     * @return 分页对象
     */
    PageInfo<SysUserDTO> selectUnAuthUserPage(@Param("page") PageInfo<?> pageInfo, @Param("qry") SysAuthUserPageQuery qry);

    /**
     * 重置用户密码
     *
     * @param userId   用户id
     * @param password 重置密码
     * @return 影响条数
     */
    Integer resetPassword(@Param("userId") Long userId, @Param("password") String password);
}
