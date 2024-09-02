package com.freeying.admin.sys.domain.query;

import com.freeying.common.core.entity.LocalDateTimeRange;
import com.freeying.common.core.entity.Query;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import com.freeying.admin.sys.domain.dto.SysUserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SysUserPageQuery
 * <p>系统用户查询对象</p>
 *
 * @author fx
 */
@Schema(description = "系统用户分页查询对象(v1)")
public class SysUserPageQuery extends Query<SysUserDTO> {

    @Schema(description = "关键字(用户名/昵称/手机号)")
    private String keywords;

    @Schema(description = "用户状态")
    private Integer status;

    @Schema(description = "创建时间范围")
    private LocalDateTimeRange gmtCreate;

    @Schema(description = "部门ID")
    @ValidLong(enableEmpty = true)
    private String deptId;

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

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("keywords", keywords)
                .append("status", status)
                .append("gmtCreate", gmtCreate)
                .append("deptId", deptId)
                .toString();
    }
}
