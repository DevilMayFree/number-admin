package com.freeying.admin.sys.domain.dto;

import com.freeying.common.core.entity.Node;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * SysDeptDTO
 * <p>系统部门返回对象</p>
 *
 * @author fx
 */
@Schema(description = "系统部门列表返回数据")
public class SysDeptDTO extends Node<SysDeptDTO> {

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "描述")
    private String remark;

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

    public SysDeptDTO() {
        // something
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
                .append("id", getId())
                .append("pid", getPid())
                .append("name", name)
                .append("sort", sort)
                .append("remark", remark)
                .append("status", status)
                .append("createBy", createBy)
                .append("gmtCreate", gmtCreate)
                .append("updateBy", updateBy)
                .append("gmtModified", gmtModified)
                .append("children", getChildren())
                .toString();
    }
}
