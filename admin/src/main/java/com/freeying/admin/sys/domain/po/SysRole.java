package com.freeying.admin.sys.domain.po;

import com.freeying.framework.data.core.BasePO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SysRole extends BasePO {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;

    /**
     * 角色备注
     */
    private String remark;

    /**
     * 启用标识 1-启用，0-禁用
     */
    private Integer status;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("name", name)
                .append("code", code)
                .append("remark", remark)
                .append("status", status)
                .toString();
    }
}
