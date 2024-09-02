package com.freeying.admin.sys.controller;

import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.web.PageInfo;
import com.freeying.common.core.web.Result;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import com.freeying.framework.data.core.IdCmd;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.framework.security.utils.SecurityUtil;
import com.freeying.admin.sys.domain.command.SysUserCommand;
import com.freeying.admin.sys.domain.dto.SysRoleDTO;
import com.freeying.admin.sys.domain.dto.SysUserDTO;
import com.freeying.admin.sys.domain.dto.SysUserDetailDTO;
import com.freeying.admin.sys.domain.dto.SysUserInfoDTO;
import com.freeying.admin.sys.domain.query.SysAuthUserPageQuery;
import com.freeying.admin.sys.domain.query.SysUserPageQuery;
import com.freeying.admin.sys.service.SysRoleService;
import com.freeying.admin.sys.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/sys/user")
@Tag(description = "sysUser", name = "系统用户管理模块")
public class SysUserController {
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    public SysUserController(SysUserService sysUserService,
                             SysRoleService sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/info", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "查询当前用户详细信息v1", description = "查询当前用户详细信息")
    public Result<SysUserInfoDTO> info() {
        SysUserDTO info = sysUserService.info(SecurityUtil.getCurrentUserId());
        Set<String> roles = SecurityUtil.getCurrentRole();
        Set<String> permissions = SecurityUtil.getCurrentPermission();
        return Result.success(new SysUserInfoDTO(info, roles, permissions));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询用户列表v1", description = "查询用户列表数据")
    @PreAuthorize("@as.hasAuthority('sys:user:list')")
    public Result<PageInfo<SysUserDTO>> page(@Validated @RequestBody SysUserPageQuery qry) {
        PageInfo<SysUserDTO> page = sysUserService.page(qry);
        return Result.success(page);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/{userId}")
    @PreAuthorize("@as.hasAuthority('sys:user:list')")
    @Operation(summary = "查询用户详情v1", description = "查询用户详情数据")
    public Result<SysUserDetailDTO> id(@PathVariable @ValidLong String userId) {
        SysUserDTO user = sysUserService.info(Long.valueOf(userId));
        List<SysRoleDTO> list = sysRoleService.selectRoleByUserId(Long.valueOf(userId));
        return Result.success(new SysUserDetailDTO(user, list));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/authUser/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询该角色已分配用户列表v1", description = "分页查询该角色已分配用户列表")
    @PreAuthorize("@as.hasAuthority('sys:role:list')")
    public Result<PageInfo<SysUserDTO>> authUserPage(@Validated @RequestBody SysAuthUserPageQuery qry) {
        PageInfo<SysUserDTO> page = sysUserService.authUserPage(qry);
        return Result.success(page);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/authRole/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询该用户已分配角色列表v1", description = "分页查询该用户已分配角色列表")
    @PreAuthorize("@as.hasAuthority('sys:role:list')")
    public Result<SysUserDetailDTO> authRolePage(@Validated @RequestBody IdCmd id) {
        SysUserDTO user = sysUserService.info(id.getLongId());
        List<SysRoleDTO> list = sysRoleService.selectRoleAllByFlag(id.getLongId());
        return Result.success(new SysUserDetailDTO(user, list));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/unAuthUser/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询该角色未分配用户列表v1", description = "分页查询该角色未分配用户列表")
    @PreAuthorize("@as.hasAuthority('sys:role:list')")
    public Result<PageInfo<SysUserDTO>> unAuthUserPage(@Validated @RequestBody SysAuthUserPageQuery qry) {
        PageInfo<SysUserDTO> page = sysUserService.unAuthUserPage(qry);
        return Result.success(page);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/add", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "新增用户v1", description = "新增用户")
    @PreAuthorize("@as.hasAuthority('sys:user:add')")
    public Result<Boolean> add(@Validated(value = SysUserCommand.Created.class)
                               @RequestBody SysUserCommand com) {
        return Result.status(sysUserService.add(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/edit", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "编辑用户v1", description = "编辑用户")
    @PreAuthorize("@as.hasAuthority('sys:user:edit')")
    public Result<Boolean> edit(@Validated(value = SysUserCommand.Update.class)
                                @RequestBody SysUserCommand com) {
        return Result.status(sysUserService.edit(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @DeleteMapping(value = "/del", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "删除用户v1", description = "删除用户")
    @PreAuthorize("@as.hasAuthority('sys:user:del')")
    public Result<Boolean> del(@Validated @RequestBody IdCmdList ids) {
        return Result.status(sysUserService.batchDel(ids));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/resetPwd", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "重置用户密码v1", description = "重置用户密码")
    @PreAuthorize("@as.hasAuthority('sys:user:edit')")
    public Result<Boolean> resetPwd(@Validated @RequestBody IdCmd id) {
        return Result.status(sysUserService.resetPwd(id));
    }

}
