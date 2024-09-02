package com.freeying.admin.sys.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.webmvc.validation.constraints.ValidIdList;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * SysUserRoleCommand
 * <p>系统角色操作对象</p>
 *
 * @author fx
 */
@Schema(description = "系统用户角色关联操作对象(v1)")
public class SysUserRoleCommand extends Command {

    @ValidLong(groups = {UnAuth.class, Auth.class})
    @Schema(description = "角色id")
    private String roleId;

    @ValidIdList(groups = {UnAuth.class, Auth.class})
    @Schema(description = "用户id列表")
    private List<String> userIds;

    public @interface UnAuth {
    }

    public @interface Auth {

    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("roleId", roleId)
                .append("userIds", userIds)
                .toString();
    }
}
