package com.freeying.admin.sys.support;

import cn.hutool.core.collection.CollUtil;
import com.freeying.common.core.enums.StatusEnum;
import com.freeying.admin.sys.domain.dto.RouteDTO;
import com.freeying.admin.sys.domain.enums.ResourceTypeEnum;
import com.freeying.admin.sys.domain.enums.VisibleEnum;
import com.freeying.admin.sys.domain.po.SysResource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * RouteHandler
 * <p>路由生成工具</p>
 *
 * @author fx
 */
public final class RouteHandler {

    private RouteHandler() {
    }

    /**
     * 递归生成菜单路由层级列表
     *
     * @param parentId     父级ID
     * @param resourceList 菜单列表
     * @return List
     */
    public static List<RouteDTO> recurRoutes(Long parentId, List<SysResource> resourceList) {
        List<RouteDTO> list = new ArrayList<>();

        Optional.ofNullable(resourceList).ifPresent(menus -> menus.stream()
                .filter(menu -> menu.getPid().equals(parentId))
                .sorted(Comparator.comparingInt(SysResource::getSort))
                .forEach(res -> {
                    RouteDTO route = new RouteDTO();
                    ResourceTypeEnum resourceTypeEnum = ResourceTypeEnum.getEnum(res.getType());
                    if (ResourceTypeEnum.MENU.equals(resourceTypeEnum)) {
                        route.setName(res.getPath());
                    }
                    route.setPath(res.getPath());
                    route.setComponent(res.getComponent());
                    RouteDTO.Meta meta = createMeta(res);
                    route.setMeta(meta);

                    List<RouteDTO> children = recurRoutes(res.getId(), resourceList);
                    // 含有子节点的目录设置为可见
                    boolean alwaysShow = CollUtil.isNotEmpty(children)
                            && children.stream().anyMatch(item -> item.getMeta().getHidden().equals(false));
                    meta.setAlwaysShow(alwaysShow);
                    route.setChildren(children);

                    list.add(route);
                }));
        return list;
    }

    /**
     * 创建meta对象
     *
     * @param res ResourcePo
     * @return RouteDTO.Meta
     */
    private static RouteDTO.Meta createMeta(SysResource res) {
        RouteDTO.Meta meta = new RouteDTO.Meta();
        meta.setTitle(res.getName());
        meta.setIcon(res.getIcon());
        meta.setHidden(VisibleEnum.getEnum(res.getVisible()).getHidden());
        meta.setKeepAlive(StatusEnum.ENABLE.getValue().equals(res.getKeepAlive()));
        return meta;
    }
}

