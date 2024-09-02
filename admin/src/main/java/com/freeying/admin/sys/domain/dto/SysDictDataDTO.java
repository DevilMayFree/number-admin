package com.freeying.admin.sys.domain.dto;

import com.freeying.common.core.entity.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * SysDictDataDTO
 * <p>系统字典数据列表返回数据</p>
 *
 * @author fx
 */
@Schema(description = "系统字典数据列表返回数据")
public class SysDictDataDTO extends DTO {

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "字典类型id")
    private Long dictId;

    @Schema(description = "字典项显示文本")
    private String label;

    @Schema(description = "字典项值")
    private String value;

    @Schema(description = "字典项描述")
    private String remark;

    @Schema(description = "字典项排序")
    private Integer sort;

    @Schema(description = "1-启用，0-禁用")
    private Integer status;

    @Schema(description = "创建人")
    private Long createBy;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;

    @Schema(description = "更新人")
    private Long updateBy;

    @Schema(description = "更新时间")
    private LocalDateTime gmtModified;

    public SysDictDataDTO() {
        // something
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("dictId", dictId)
                .append("label", label)
                .append("value", value)
                .append("remark", remark)
                .append("sort", sort)
                .append("status", status)
                .append("createBy", createBy)
                .append("gmtCreate", gmtCreate)
                .append("updateBy", updateBy)
                .append("gmtModified", gmtModified)
                .toString();
    }
}
