package com.freeying.admin.sys.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.core.enums.StatusEnum;
import com.freeying.common.webmvc.validation.constraints.ValidEnum;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SysDictTypeCommand
 * <p>系统字典类型操作对象</p>
 *
 * @author fx
 */
@Schema(description = "系统字典类型操作对象(v1)")
public class SysDictTypeCommand extends Command {

    @ValidLong(groups = Update.class)
    @Schema(description = "id")
    private String id;

    @NotBlank
    @Schema(description = "字典名")
    private String name;

    @NotBlank
    @Schema(description = "字典编码")
    private String code;

    @ValidEnum(enumClass = StatusEnum.class, enumMethod = "isValidValue")
    @Schema(description = "1-启用，0-禁用")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("code", code)
                .append("status", status)
                .append("remark", remark)
                .toString();
    }
}
