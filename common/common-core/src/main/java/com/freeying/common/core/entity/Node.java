package com.freeying.common.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * TreeNode
 *
 * @author fx
 */
public abstract class Node<T extends Node<T>> extends DTO {

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "父级id")
    private Long pid;

    @Schema(description = "子部门")
    private List<T> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
