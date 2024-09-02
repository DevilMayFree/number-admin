package com.freeying.admin.sys.mapper;

import com.freeying.framework.data.core.PlusBaseMapper;
import com.freeying.admin.sys.domain.dto.SysResourceDTO;
import com.freeying.admin.sys.domain.po.SysResource;
import com.freeying.admin.sys.domain.query.SysResourceListQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysResourceMapper extends PlusBaseMapper<SysResource> {

    /**
     * 查询当前角色拥有的资源
     *
     * @param roleIds 当前用户角色集合
     * @param types   查询资源类型集合
     * @return 资源列表
     */
    List<SysResource> selectResource(@Param("roleIds") Set<Long> roleIds, @Param("types") List<Integer> types);

    /**
     * 查询当前角色拥有的资源
     *
     * @param roleIds 当前用户角色集合
     * @param types   查询资源类型集合
     * @return 资源列表返回数据
     */
    List<SysResourceDTO> selectResourceDTO(@Param("roleIds") Set<Long> roleIds, @Param("types") List<Integer> types);

    /**
     * 查询当前角色拥有的资源列表
     *
     * @param roleIds 当前用户角色集合
     * @param types   查询资源类型集合
     * @param qry     SysResourceListQuery
     * @return 资源列表返回数据
     */
    List<SysResourceDTO> selectResourceDTOList(@Param("roleIds") Set<Long> roleIds,
                                               @Param("types") List<Integer> types,
                                               @Param("qry") SysResourceListQuery qry);

    /**
     * 查询当前角色拥有的资源列表
     *
     * @param types 查询资源类型集合
     * @param qry   SysResourceListQuery
     * @return 资源列表返回数据
     */
    List<SysResourceDTO> selectResourceDTOListByAdmin(@Param("types") List<Integer> types, @Param("qry") SysResourceListQuery qry);

    /**
     * 查询角色拥有的资源id
     *
     * @param roleId 查询角色id
     * @return 资源id列表
     */
    List<Long> selectResourceIdByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据资源名称和父级id查询资源对象
     *
     * @param name 资源名称
     * @param pid  父级id
     * @return 资源对象
     */
    SysResource selectSysResourceByNamePid(@Param("name") String name, @Param("pid") Long pid);

    /**
     * 根据父级id查询资源对象
     *
     * @param pid  父级id
     * @return 资源对象
     */
    SysResource selectSysResourceByPid(@Param("pid") Long pid);
}
