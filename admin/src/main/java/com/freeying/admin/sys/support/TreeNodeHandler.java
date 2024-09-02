package com.freeying.admin.sys.support;

import com.freeying.common.core.entity.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TreeNodeHandler
 * <p>树结构生成工具</p>
 *
 * @author fx
 */
public final class TreeNodeHandler {

    private TreeNodeHandler() {
    }

    /**
     * 递归生成树级列表
     *
     * @param parentId 父级ID
     * @param nodeList 节点列表
     * @return 树级列表
     */
    public static <T extends Node<T>> List<T> recurNodes(Long parentId, List<T> nodeList) {
        List<T> list = new ArrayList<>();

        Optional.ofNullable(nodeList).ifPresent(n -> n.stream()
                .filter(node -> node.getPid().equals(parentId))
                .forEach(res -> {
                    List<T> children = recurNodes(res.getId(), nodeList);
                    res.setChildren(children);
                    list.add(res);
                }));
        return list;
    }

}

