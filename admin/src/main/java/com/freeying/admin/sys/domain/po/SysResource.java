package com.freeying.admin.sys.domain.po;

import com.freeying.framework.data.core.BasePO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SysResource extends BasePO {

    /**
     * 上级Id
     */
    private Long pid;

    /**
     * 资源名
     */
    private String name;

    /**
     * 资源编码
     */
    private String code;

    /**
     * 资源描述
     */
    private String remark;

    /**
     * 资源类型
     */
    private Integer type;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 缓存
     */
    private Integer keepAlive;

    /**
     * 显示状态
     */
    private Integer visible;

    /**
     * 状态 1-启用 0-禁用
     */
    private Integer status;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Integer keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("pid", pid)
                .append("name", name)
                .append("code", code)
                .append("remark", remark)
                .append("type", type)
                .append("path", path)
                .append("component", component)
                .append("icon", icon)
                .append("sort", sort)
                .append("keepAlive", keepAlive)
                .append("visible", visible)
                .append("status", status)
                .toString();
    }
}
