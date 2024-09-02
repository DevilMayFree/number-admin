package com.freeying.admin.sys.controller;

import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.constant.SystemConstants;
import com.freeying.common.core.web.Result;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import com.freeying.framework.data.core.IdCmd;
import com.freeying.admin.sys.domain.command.SysDeptCommand;
import com.freeying.admin.sys.domain.dto.SysDeptDTO;
import com.freeying.admin.sys.domain.query.SysDeptListQuery;
import com.freeying.admin.sys.service.SysDeptService;
import com.freeying.admin.sys.support.TreeNodeHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sys/dept")
@Tag(description = "sysDept", name = "系统部门管理模块")
public class SysDeptController {

    private final SysDeptService sysDeptService;

    public SysDeptController(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询部门列表v1", description = "查询部门列表数据")
    @PreAuthorize("@as.hasAuthority('sys:dept:list')")
    public Result<List<SysDeptDTO>> page(@Validated @RequestBody SysDeptListQuery qry) {
        List<SysDeptDTO> list = sysDeptService.list(qry);
        if (qry.getStatus() == null && StringUtils.isBlank(qry.getKeywords())) {
            list = TreeNodeHandler.recurNodes(SystemConstants.ROOT_NODE_ID, list);
        }
        return Result.success(list);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/{deptId}")
    @Operation(summary = "查询部门详情v1", description = "查询部门详情")
    @PreAuthorize("@as.hasAuthority('sys:dept:list')")
    public Result<SysDeptDTO> id(@PathVariable Long deptId) {
        return Result.success(sysDeptService.selectDeptById(deptId));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/add", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "新增部门v1", description = "新增部门")
    @PreAuthorize("@as.hasAuthority('sys:dept:add')")
    public Result<Boolean> add(@Validated(value = SysDeptCommand.Created.class)
                               @RequestBody SysDeptCommand com) {
        return Result.status(sysDeptService.add(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/edit", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "编辑部门v1", description = "编辑部门")
    @PreAuthorize("@as.hasAuthority('sys:dept:edit')")
    public Result<Boolean> edit(@Validated(value = SysDeptCommand.Update.class)
                                @RequestBody SysDeptCommand com) {
        return Result.status(sysDeptService.edit(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @DeleteMapping(value = "/del", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "删除部门v1", description = "删除部门")
    @PreAuthorize("@as.hasAuthority('sys:dept:del')")
    public Result<Boolean> del(@Validated @RequestBody IdCmd id) {
        return Result.status(sysDeptService.del(id));
    }

}
