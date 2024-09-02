package com.freeying.admin.sys.service;

import com.freeying.framework.data.core.IdCmd;
import com.freeying.admin.sys.domain.command.SysResourceCommand;
import com.freeying.admin.sys.domain.dto.RouteDTO;
import com.freeying.admin.sys.domain.dto.SysResourceDTO;
import com.freeying.admin.sys.domain.query.SysResourceListQuery;

import java.util.List;

/**
 * SysResourceService
 *
 * @author fx
 */
public interface SysResourceService {

    /**
     * 构建前端路由
     *
     * @param userId 当前用户id
     * @return List<RouteDTO>
     */
    List<RouteDTO> listRoutes(Long userId);

    /**
     * 构建资源树
     *
     * @param userId 当前用户id
     * @return List<SysResourceDTO>
     */
    List<SysResourceDTO> treeSelect(Long userId);

    /**
     * 查询角色对应的资源树
     *
     * @param roleId 角色id
     * @return 资源id列表
     */
    List<Long> roleSelect(Long roleId);

    /**
     * 前端查询资源列表
     *
     * @param qry SysResourceListQuery
     * @return 资源列表
     */
    List<SysResourceDTO> list(SysResourceListQuery qry);

    /**
     * 根据id查询对应的资源DTO
     *
     * @param id 资源id
     * @return 资源对象
     */
    SysResourceDTO selectResourceById(Long id);

    /**
     * 新增资源
     *
     * @param com SysResourceCommand
     * @return boolean
     */
    boolean add(SysResourceCommand com);

    /**
     * 编辑资源
     *
     * @param com SysResourceCommand
     * @return boolean
     */
    boolean edit(SysResourceCommand com);

    /**
     * 删除资源
     *
     * @param id 资源id
     * @return boolean
     */
    boolean del(IdCmd id);
}
