package com.freeying.admin.sys.domain.dto;

import com.freeying.common.core.entity.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * SysRoleDTO
 * <p>系统角色返回数据</p>
 *
 * @author fx
 */
@Schema(description = "系统角色返回数据")
public class SysRoleDTO extends DTO {

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "角色名")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色备注")
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

    @Schema(description = "用户是否存在此角色")
    private Boolean flag;

    public SysRoleDTO() {
        // something
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("code", code)
                .append("remark", remark)
                .append("status", status)
                .append("createBy", createBy)
                .append("gmtCreate", gmtCreate)
                .append("updateBy", updateBy)
                .append("gmtModified", gmtModified)
                .append("flag", flag)
                .toString();
    }
}
