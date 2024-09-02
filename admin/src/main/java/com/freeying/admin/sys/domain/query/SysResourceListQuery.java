package com.freeying.admin.sys.domain.query;

import com.freeying.common.core.enums.StatusEnum;
import com.freeying.common.webmvc.validation.constraints.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SysResourceListQuery
 * <p>系统资源列表查询对象</p>
 *
 * @author fx
 */
@Schema(description = "系统资源列表查询对象(v1)")
public class SysResourceListQuery {

    @Schema(description = "关键字(名称)")
    private String keywords;

    @Schema(description = "状态")
    @ValidEnum(enableEmpty = true, enumClass = StatusEnum.class, enumMethod = "isValidValue")
    private Integer status;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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
                .append("keywords", keywords)
                .append("status", status)
                .toString();
    }
}
