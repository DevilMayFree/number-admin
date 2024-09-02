package com.freeying.admin.sys.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.core.enums.StatusEnum;
import com.freeying.common.webmvc.validation.constraints.ValidEnum;
import com.freeying.common.webmvc.validation.constraints.ValidIdList;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * SysRoleCommand
 * <p>系统角色操作对象</p>
 *
 * @author fx
 */
@Schema(description = "系统角色操作对象(v1)")
public class SysRoleCommand extends Command {

    @ValidLong(groups = {SysUserCommand.Created.class, Update.class})
    @Schema(description = "id")
    private String id;

    @NotBlank
    @Schema(description = "角色名")
    private String name;

    @NotBlank
    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色备注")
    private String remark;

    @ValidEnum(enumClass = StatusEnum.class, enumMethod = "isValidValue")
    @Schema(description = "状态 1-启用 0-禁用")
    private Integer status;

    @ValidIdList(groups = {Created.class})
    @Schema(description = "关联资源id合集")
    private List<String> resourceIds;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("code", code)
                .append("remark", remark)
                .append("status", status)
                .append("resourceIds", resourceIds)
                .toString();
    }
}
