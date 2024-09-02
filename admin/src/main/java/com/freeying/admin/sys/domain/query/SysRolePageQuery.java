package com.freeying.admin.sys.domain.query;

import com.freeying.common.core.entity.LocalDateTimeRange;
import com.freeying.common.core.entity.Query;
import com.freeying.common.core.enums.StatusEnum;
import com.freeying.common.webmvc.validation.constraints.ValidEnum;
import com.freeying.admin.sys.domain.dto.SysRoleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SysRolePageQuery
 * <p>系统角色分页查询对象</p>
 *
 * @author fx
 */
@Schema(description = "系统角色分页查询对象(v1)")
public class SysRolePageQuery extends Query<SysRoleDTO> {

    @Schema(description = "关键字(角色名称/角色编码)")
    private String keywords;

    @Schema(description = "角色状态")
    @ValidEnum(enableEmpty = true, enumClass = StatusEnum.class, enumMethod = "isValidValue")
    private Integer status;

    @Schema(description = "创建时间范围")
    private LocalDateTimeRange gmtCreate;

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

    public LocalDateTimeRange getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTimeRange gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("keywords", keywords)
                .append("status", status)
                .append("gmtCreate", gmtCreate)
                .toString();
    }
}
