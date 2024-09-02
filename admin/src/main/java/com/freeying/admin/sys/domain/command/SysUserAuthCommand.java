package com.freeying.admin.sys.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.webmvc.validation.constraints.ValidIdList;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * SysUserAuthCommand
 * <p>系统角色操作对象</p>
 *
 * @author fx
 */
@Schema(description = "系统用户关联多角色操作对象(v1)")
public class SysUserAuthCommand extends Command {

    @ValidLong
    @Schema(description = "用户id")
    private String userId;

    @ValidIdList
    @Schema(description = "角色id列表")
    private List<String> roleIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userId", userId)
                .append("roleIds", roleIds)
                .toString();
    }
}
