package com.freeying.admin.sys.domain.query;

import com.freeying.common.core.entity.Query;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import com.freeying.admin.sys.domain.dto.SysUserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SysAuthUserPageQuery
 * <p>系统角色已分配用户查询对象</p>
 *
 * @author fx
 */
@Schema(description = "系统角色已分配用户分页查询对象(v1)")
public class SysAuthUserPageQuery extends Query<SysUserDTO> {

    @Schema(description = "关键字(用户名/昵称/手机号)")
    private String keywords;

    @ValidLong
    @Schema(description = "角色id")
    private String roleId;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("keywords", keywords)
                .append("roleId", roleId)
                .toString();
    }
}
