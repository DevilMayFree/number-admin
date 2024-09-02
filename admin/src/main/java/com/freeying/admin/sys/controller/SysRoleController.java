package com.freeying.admin.sys.controller;

import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.web.PageInfo;
import com.freeying.common.core.web.Result;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysRoleCommand;
import com.freeying.admin.sys.domain.command.SysUserAuthCommand;
import com.freeying.admin.sys.domain.command.SysUserRoleCommand;
import com.freeying.admin.sys.domain.dto.SysRoleDTO;
import com.freeying.admin.sys.domain.query.SysRoleListQuery;
import com.freeying.admin.sys.domain.query.SysRolePageQuery;
import com.freeying.admin.sys.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sys/role")
@Tag(description = "sysRole", name = "系统角色管理模块")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询角色列表v1", description = "查询角色列表数据")
    @PreAuthorize("@as.hasAuthority('sys:role:list')")
    public Result<PageInfo<SysRoleDTO>> page(@Validated @RequestBody SysRolePageQuery qry) {
        PageInfo<SysRoleDTO> page = sysRoleService.page(qry);
        return Result.success(page);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/list", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询角色列表v1", description = "查询角色列表数据")
    @PreAuthorize("@as.hasAuthority('sys:role:list')")
    public Result<List<SysRoleDTO>> list(@Validated @RequestBody SysRoleListQuery qry) {
        List<SysRoleDTO> dtoList = sysRoleService.list(qry);
        return Result.success(dtoList);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/{roleId}")
    @PreAuthorize("@as.hasAuthority('sys:role:list')")
    @Operation(summary = "查询角色详情v1", description = "查询角色详情数据")
    public Result<SysRoleDTO> id(@PathVariable @NotEmpty Long roleId) {
        return Result.success(sysRoleService.selectRoleById(roleId));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/add", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "新增角色v1", description = "新增角色")
    @PreAuthorize("@as.hasAuthority('sys:role:add')")
    public Result<Boolean> add(@Validated(value = SysRoleCommand.Created.class)
                               @RequestBody SysRoleCommand com) {
        return Result.status(sysRoleService.add(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/edit", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "编辑角色v1", description = "编辑角色")
    @PreAuthorize("@as.hasAuthority('sys:role:edit')")
    public Result<Boolean> edit(@Validated(value = SysRoleCommand.Update.class)
                                @RequestBody SysRoleCommand com) {
        return Result.status(sysRoleService.edit(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @DeleteMapping(value = "/del", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "删除角色v1", description = "删除角色")
    @PreAuthorize("@as.hasAuthority('sys:role:del')")
    public Result<Boolean> del(@Validated @RequestBody IdCmdList ids) {
        return Result.status(sysRoleService.batchDel(ids));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @DeleteMapping(value = "/authCancel", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "批量取消授权v1", description = "批量取消授权")
    @PreAuthorize("@as.hasAuthority('sys:role:del')")
    public Result<Boolean> authCancel(@Validated(value = SysUserRoleCommand.UnAuth.class)
                                      @RequestBody SysUserRoleCommand com) {
        return Result.status(sysRoleService.authCancel(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/userAuth", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "批量用户授权v1", description = "批量用户授权")
    @PreAuthorize("@as.hasAuthority('sys:role:edit')")
    public Result<Boolean> userAuth(@Validated(value = SysUserRoleCommand.Auth.class)
                                    @RequestBody SysUserRoleCommand com) {
        return Result.status(sysRoleService.userAuth(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/roleAuth", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "批量角色授权v1", description = "批量角色授权")
    @PreAuthorize("@as.hasAuthority('sys:user:edit')")
    public Result<Boolean> userAuth(@Validated @RequestBody SysUserAuthCommand com) {
        return Result.status(sysRoleService.roleAuth(com));
    }

}
