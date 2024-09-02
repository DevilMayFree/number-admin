package com.freeying.admin.sys.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.core.enums.StatusEnum;
import com.freeying.common.webmvc.validation.constraints.ValidEnum;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SysDeptCommand
 * <p>系统部门操作对象</p>
 *
 * @author fx
 */
@Schema(description = "系统部门操作对象(v1)")
public class SysDeptCommand extends Command {

    @ValidLong(groups = Update.class)
    @Schema(description = "id")
    private String id;

    @ValidLong
    @Schema(description = "父级id")
    private String pid;

    @NotBlank
    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "备注")
    private String remark;

    @ValidEnum(enumClass = StatusEnum.class, enumMethod = "isValidValue")
    @Schema(description = "1-启用，0-禁用")
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("pid", pid)
                .append("name", name)
                .append("sort", sort)
                .append("remark", remark)
                .append("status", status)
                .toString();
    }
}
