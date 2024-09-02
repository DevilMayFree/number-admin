package com.freeying.admin.sys.domain.query;

import com.freeying.common.core.entity.DTO;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SysRoleListQuery
 * <p>系统角色列表查询对象</p>
 *
 * @author fx
 */
@Schema(description = "系统角色分页查询对象(v1)")
public class SysRoleListQuery extends DTO {

    @ValidLong(enableEmpty = true)
    @Schema(description = "用户id")
    private String userId;

    public SysRoleListQuery() {
    }

    public SysRoleListQuery(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userId", userId)
                .toString();
    }
}
