package com.freeying.framework.data.core;

import com.baomidou.mybatisplus.annotation.*;
import org.apache.commons.lang3.builder.ToStringBuilder;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base persistent object
 *
 * @author fx
 */
public class BasePO implements Serializable {

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    // 租户ID
    // Long tenantId

    /**
     * 删除标记
     */
    @TableLogic(delval = "1", value = "0")
    private Integer deleted;

    /**
     * 乐观锁
     */
    @Version
    private Long version;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

    public BasePO() {
    }

    public BasePO(Long id, Integer deleted, Long version, Long createBy,
                  LocalDateTime gmtCreate, Long updateBy, LocalDateTime gmtModified) {
        this.id = id;
        this.deleted = deleted;
        this.version = version;
        this.createBy = createBy;
        this.gmtCreate = gmtCreate;
        this.updateBy = updateBy;
        this.gmtModified = gmtModified;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
                .append("deleted", deleted)
                .append("version", version)
                .append("createBy", createBy)
                .append("gmtCreate", gmtCreate)
                .append("updateBy", updateBy)
                .append("gmtModified", gmtModified)
                .toString();
    }
}
