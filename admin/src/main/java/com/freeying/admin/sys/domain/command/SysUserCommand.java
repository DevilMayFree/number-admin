package com.freeying.admin.sys.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.core.enums.StatusEnum;
import com.freeying.common.webmvc.validation.constraints.ValidEnum;
import com.freeying.common.webmvc.validation.constraints.ValidIdList;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import com.freeying.common.webmvc.validation.constraints.ValidMobile;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * SysUserCommand
 * <p>系统用户操作对象</p>
 *
 * @author fx
 */
@Schema(description = "系统用户操作对象(v1)")
public class SysUserCommand extends Command {

    @ValidLong(groups = Update.class)
    @Schema(description = "id")
    private String id;

    @NotBlank
    @Schema(description = "用户名")
    private String username;

    @NotBlank
    @Schema(description = "昵称")
    private String nickname;

    @ValidMobile
    @Schema(description = "手机号")
    private String phoneNumber;

    @Email
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像")
    private String avatar;

    @ValidLong
    @Schema(description = "部门Id")
    private String deptId;

    @ValidEnum(enumClass = StatusEnum.class, enumMethod = "isValidValue")
    @Schema(description = "1-启用，0-禁用")
    private Integer status;

    @ValidIdList
    @Schema(description = "角色id列表")
    private List<String> roleIds;

    public @interface Created {
    }

    public @interface Update {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
                .append("id", id)
                .append("username", username)
                .append("nickname", nickname)
                .append("phoneNumber", phoneNumber)
                .append("email", email)
                .append("avatar", avatar)
                .append("deptId", deptId)
                .append("status", status)
                .append("roleIds", roleIds)
                .toString();
    }
}
