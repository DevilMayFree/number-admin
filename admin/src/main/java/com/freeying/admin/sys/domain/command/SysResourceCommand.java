package com.freeying.admin.sys.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.core.enums.StatusEnum;
import com.freeying.common.webmvc.validation.constraints.ValidEnum;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import com.freeying.admin.sys.domain.enums.ResourceTypeEnum;
import com.freeying.admin.sys.domain.enums.VisibleEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SysResourceCommand
 * <p>系统资源操作对象</p>
 *
 * @author fx
 */
@Schema(description = "系统资源操作对象(v1)")
public class SysResourceCommand extends Command {

    @ValidLong(groups = {SysRoleCommand.Created.class, Update.class})
    @Schema(description = "id")
    private String id;

    @ValidLong
    @Schema(description = "父级id")
    private String pid;

    @NotBlank
    @Schema(description = "资源名")
    private String name;

    @NotBlank
    @Schema(description = "资源编码")
    private String code;

    @Schema(description = "资源描述")
    private String remark;

    @ValidEnum(enumClass = ResourceTypeEnum.class, enumMethod = "isValidValue")
    @Schema(description = "资源类型")
    private Integer type;

    @NotBlank
    @Schema(description = "路径")
    private String path;

    @NotBlank
    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序值")
    private Integer sort;

    @ValidEnum(enableEmpty = true, enumClass = StatusEnum.class, enumMethod = "isValidValue")
    @Schema(description = "缓存")
    private Integer keepAlive;

    @ValidEnum(enumClass = VisibleEnum.class, enumMethod = "isValidValue")
    @Schema(description = "显示状态")
    private Integer visible;

    @ValidEnum(enumClass = StatusEnum.class, enumMethod = "isValidValue")
    @Schema(description = "状态 1-启用 0-禁用")
    private Integer status;

    public @interface Created {
    }

    public @interface Update {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
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
                .append("id", id)
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
