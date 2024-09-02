package com.freeying.admin.sys.domain.dto;

import com.freeying.common.core.entity.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Set;

/**
 * SysUserInfoDTO
 * <p>系统用户信息所有数据</p>
 *
 * @author fx
 */
@Schema(description = "系统用户信息所有数据")
public class SysUserInfoDTO extends DTO {

    @Schema(description = "系统用户对象返回数据")
    private SysUserDTO user;

    @Schema(description = "系统角色返回数据")
    private Set<String> roles;

    @Schema(description = "系统权限返回数据")
    private Set<String> permissions;

    public SysUserInfoDTO() {
        // empty
    }

    public SysUserInfoDTO(SysUserDTO user, Set<String> roles, Set<String> permissions) {
        this.user = user;
        this.roles = roles;
        this.permissions = permissions;
    }

    public SysUserDTO getUser() {
        return user;
    }

    public void setUser(SysUserDTO user) {
        this.user = user;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("user", user)
                .append("roles", roles)
                .append("permissions", permissions)
                .toString();
    }
}
