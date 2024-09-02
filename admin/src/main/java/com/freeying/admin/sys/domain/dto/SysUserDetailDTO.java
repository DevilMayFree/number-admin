package com.freeying.admin.sys.domain.dto;

import com.freeying.common.core.entity.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * SysUserDetailDTO
 * <p>系统用户返回对象</p>
 *
 * @author fx
 */
@Schema(description = "系统用户列表用户详情数据")
public class SysUserDetailDTO extends DTO {

    @Schema(description = "系统用户对象返回数据")
    private SysUserDTO user;

    @Schema(description = "系统角色返回数据")
    private List<SysRoleDTO> roles;

    public SysUserDetailDTO() {
        // empty
    }

    public SysUserDetailDTO(SysUserDTO user, List<SysRoleDTO> roles) {
        this.user = user;
        this.roles = roles;
    }

    public SysUserDTO getUser() {
        return user;
    }

    public void setUser(SysUserDTO user) {
        this.user = user;
    }

    public List<SysRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("user", user)
                .append("roles", roles)
                .toString();
    }
}
