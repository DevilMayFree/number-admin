package com.freeying.admin.sys.controller;

import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.web.PageInfo;
import com.freeying.common.core.web.Result;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysDictTypeCommand;
import com.freeying.admin.sys.domain.dto.SysDictTypeDTO;
import com.freeying.admin.sys.domain.query.SysDictTypePageQuery;
import com.freeying.admin.sys.service.SysDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sys/dict/type")
@Tag(description = "sysDictType", name = "系统字典类型管理模块")
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;

    public SysDictTypeController(SysDictTypeService sysDictTypeService) {
        this.sysDictTypeService = sysDictTypeService;
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询字典类型列表v1", description = "查询字典类型列表数据")
    @PreAuthorize("@as.hasAuthority('sys:dict:list')")
    public Result<PageInfo<SysDictTypeDTO>> page(@Validated @RequestBody SysDictTypePageQuery qry) {
        PageInfo<SysDictTypeDTO> page = sysDictTypeService.page(qry);
        return Result.success(page);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/all")
    @PreAuthorize("@as.hasAuthority('sys:dict:list')")
    @Operation(summary = "查询所有字典类型v1", description = "查询所有字典类型数据")
    public Result<List<SysDictTypeDTO>> all() {
        return Result.success(sysDictTypeService.all());
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/{dictId}")
    @PreAuthorize("@as.hasAuthority('sys:dict:list')")
    @Operation(summary = "查询字典类型v1", description = "查询字典类型数据")
    public Result<SysDictTypeDTO> id(@PathVariable Long dictId) {
        return Result.success(sysDictTypeService.selectDictTypeById(dictId));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/add", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "新增字典类型v1", description = "新增字典类型")
    @PreAuthorize("@as.hasAuthority('sys:dict:add')")
    public Result<Boolean> add(@Validated(value = SysDictTypeCommand.Created.class)
                               @RequestBody SysDictTypeCommand com) {
        return Result.status(sysDictTypeService.add(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/edit", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "编辑字典类型v1", description = "编辑字典类型")
    @PreAuthorize("@as.hasAuthority('sys:dict:edit')")
    public Result<Boolean> edit(@Validated(value = SysDictTypeCommand.Update.class)
                                @RequestBody SysDictTypeCommand com) {
        return Result.status(sysDictTypeService.edit(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @DeleteMapping(value = "/del", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "删除字典类型v1", description = "删除字典类型")
    @PreAuthorize("@as.hasAuthority('sys:dict:del')")
    public Result<Boolean> del(@Validated @RequestBody IdCmdList ids) {
        return Result.status(sysDictTypeService.batchDel(ids));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @DeleteMapping(value = "/refreshCache", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "刷新字典缓存v1", description = "刷新字典缓存")
    @PreAuthorize("@as.hasAuthority('sys:dict:del')")
    public Result<Object> refreshCache() {
        sysDictTypeService.refreshCache();
        return Result.success();
    }
}
