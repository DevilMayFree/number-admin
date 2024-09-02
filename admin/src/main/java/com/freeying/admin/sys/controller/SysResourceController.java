package com.freeying.admin.sys.controller;

import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.web.Result;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import com.freeying.framework.data.core.IdCmd;
import com.freeying.framework.security.utils.SecurityUtil;
import com.freeying.admin.sys.domain.command.SysResourceCommand;
import com.freeying.admin.sys.domain.dto.RouteDTO;
import com.freeying.admin.sys.domain.dto.SysResourceDTO;
import com.freeying.admin.sys.domain.query.SysResourceListQuery;
import com.freeying.admin.sys.service.SysResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sys/resource")
@Tag(description = "SysResource", name = "系统资源管理模块")
public class SysResourceController {

    private final SysResourceService sysResourceService;

    public SysResourceController(SysResourceService sysResourceService) {
        this.sysResourceService = sysResourceService;
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/routes", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "查询前端路由树v1", description = "根据当前用户构造对应的前端路由树")
    public Result<List<RouteDTO>> routes() {
        List<RouteDTO> dtoList = sysResourceService.listRoutes(SecurityUtil.getCurrentUserId());
        return Result.success(dtoList);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/treeSelect", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "查询前端资源树v1", description = "根据当前用户构造对应的前端资源树")
    public Result<List<SysResourceDTO>> treeSelect() {
        List<SysResourceDTO> dtoList = sysResourceService.treeSelect(SecurityUtil.getCurrentUserId());
        return Result.success(dtoList);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/roleSelect/{roleId}", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "查询前端角色对应的资源树v1", description = "查询前端角色对应的资源树")
    public Result<List<Long>> roleSelect(@PathVariable @NotEmpty Long roleId) {
        List<Long> ids = sysResourceService.roleSelect(roleId);
        return Result.success(ids);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/list", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "查询菜单列表v1", description = "查询菜单列表数据")
    @PreAuthorize("@as.hasAuthority('sys:resource:list')")
    public Result<List<SysResourceDTO>> list(@Validated @RequestBody SysResourceListQuery qry) {
        List<SysResourceDTO> list = sysResourceService.list(qry);
        return Result.success(list);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/{resourceId}")
    @PreAuthorize("@as.hasAuthority('sys:resource:list')")
    @Operation(summary = "查询资源详情v1", description = "查询资源详情")
    public Result<SysResourceDTO> id(@PathVariable @NotEmpty Long resourceId) {
        return Result.success(sysResourceService.selectResourceById(resourceId));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/add", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "新增资源v1", description = "新增资源")
    @PreAuthorize("@as.hasAuthority('sys:resource:add')")
    public Result<Boolean> add(@Validated(value = SysResourceCommand.Created.class)
                               @RequestBody SysResourceCommand com) {
        return Result.status(sysResourceService.add(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/edit", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "编辑资源v1", description = "编辑资源")
    @PreAuthorize("@as.hasAuthority('sys:resource:edit')")
    public Result<Boolean> edit(@Validated(value = SysResourceCommand.Update.class)
                                @RequestBody SysResourceCommand com) {
        return Result.status(sysResourceService.edit(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @DeleteMapping(value = "/del", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "删除资源数据v1", description = "删除资源数据")
    @PreAuthorize("@as.hasAuthority('sys:resource:del')")
    public Result<Boolean> del(@Validated @RequestBody IdCmd id) {
        return Result.status(sysResourceService.del(id));
    }
}
