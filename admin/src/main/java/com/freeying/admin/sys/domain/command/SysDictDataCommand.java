package com.freeying.admin.sys.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.core.enums.StatusEnum;
import com.freeying.common.webmvc.validation.constraints.ValidEnum;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SysDictDataCommand
 * <p>系统字典数据操作对象</p>
 *
 * @author fx
 */
@Schema(description = "系统字典数据操作对象(v1)")
public class SysDictDataCommand extends Command {

    @ValidLong(groups = Update.class)
    @Schema(description = "id")
    private String id;

    @ValidLong
    @Schema(description = "字典类型id")
    private String dictId;

    @NotBlank
    @Schema(description = "字典文本")
    private String label;

    @NotBlank
    @Schema(description = "字典值")
    private String value;

    @Schema(description = "字典排序")
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

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
                .append("dictId", dictId)
                .append("label", label)
                .append("value", value)
                .append("sort", sort)
                .append("remark", remark)
                .append("status", status)
                .toString();
    }
}
