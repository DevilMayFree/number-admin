package com.freeying.admin.sys.domain.po;

import com.freeying.framework.data.core.BasePO;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SysDictData extends BasePO {

    /**
     * 字典类型id
     */
    private Long dictId;

    /**
     * 字典项显示文本
     */
    private String label;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 字典项描述
     */
    private String remark;

    /**
     * 字典项排序
     */
    private Integer sort;

    /**
     * 启用标识 1-启用，0-禁用
     */
    private Integer status;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("dictId", dictId)
                .append("label", label)
                .append("value", value)
                .append("remark", remark)
                .append("sort", sort)
                .append("status", status)
                .toString();
    }
}
