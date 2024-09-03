package com.freeying.admin.number.controller;

import com.freeying.admin.number.domain.command.NumManagerCommand;
import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.admin.number.service.NumManagerService;
import com.freeying.admin.sys.domain.command.SysRoleCommand;
import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.web.PageInfo;
import com.freeying.common.core.web.Result;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import com.freeying.framework.data.core.IdCmdList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/num/manager")
@Tag(description = "numManager", name = "号码管理模块")
public class NumManagerController {
    private final NumManagerService numManagerService;

    public NumManagerController(NumManagerService numManagerService) {
        this.numManagerService = numManagerService;
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询号码列表v1", description = "分页查询号码列表")
    @PreAuthorize("@as.hasAuthority('num:manager:list')")
    public Result<PageInfo<NumManagerDTO>> page(@Validated @RequestBody NumManagerPageQuery qry) {
        PageInfo<NumManagerDTO> page = numManagerService.page(qry);
        return Result.success(page);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/{numId}")
    @PreAuthorize("@as.hasAuthority('num:manager:list')")
    @Operation(summary = "查询号码详情v1", description = "查询号码详情数据")
    public Result<NumManagerDTO> id(@PathVariable @NotEmpty Long numId) {
        return Result.success(numManagerService.selectNumManagerById(numId));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/add", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "新增号码v1", description = "新增号码")
    @PreAuthorize("@as.hasAuthority('num:manager:add')")
    public Result<Boolean> add(@Validated(value = NumManagerCommand.Created.class)
                               @RequestBody NumManagerCommand com) {
        return Result.status(numManagerService.add(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/edit", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "编辑号码v1", description = "编辑号码")
    @PreAuthorize("@as.hasAuthority('num:manager:edit')")
    public Result<Boolean> edit(@Validated(value = NumManagerCommand.Update.class)
                                @RequestBody NumManagerCommand com) {
        return Result.status(numManagerService.edit(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @DeleteMapping(value = "/del", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "删除号码v1", description = "删除号码")
    @PreAuthorize("@as.hasAuthority('num:manager:del')")
    public Result<Boolean> del(@Validated @RequestBody IdCmdList ids) {
        return Result.status(numManagerService.batchDel(ids));
    }

}
