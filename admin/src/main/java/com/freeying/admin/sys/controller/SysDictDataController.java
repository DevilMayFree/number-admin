package com.freeying.admin.sys.controller;

import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.web.PageInfo;
import com.freeying.common.core.web.Result;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import com.freeying.framework.data.core.IdCmdList;
import com.freeying.admin.sys.domain.command.SysDictDataCommand;
import com.freeying.admin.sys.domain.dto.SysDictDataDTO;
import com.freeying.admin.sys.domain.query.SysDictDataPageQuery;
import com.freeying.admin.sys.domain.query.SysDictDataQuery;
import com.freeying.admin.sys.service.SysDictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sys/dict/data")
@Tag(description = "sysDictData", name = "系统字典数据管理模块")
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    public SysDictDataController(SysDictDataService sysDictDataService) {
        this.sysDictDataService = sysDictDataService;
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询字典数据列表v1", description = "查询字典数据列表")
    @PreAuthorize("@as.hasAuthority('sys:dict:list')")
    public Result<PageInfo<SysDictDataDTO>> page(@Validated @RequestBody SysDictDataPageQuery qry) {
        PageInfo<SysDictDataDTO> page = sysDictDataService.page(qry);
        return Result.success(page);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/detail", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "根据字典编码查询字典数据v1", description = "根据字典编码查询字典数据")
    public Result<List<SysDictDataDTO>> detail(@Validated @RequestBody SysDictDataQuery qry) {
        return Result.success(sysDictDataService.detail(qry));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/{dataId}")
    @Operation(summary = "查询字典类型v1", description = "查询字典类型数据")
    @PreAuthorize("@as.hasAuthority('sys:dict:list')")
    public Result<SysDictDataDTO> id(@PathVariable Long dataId) {
        return Result.success(sysDictDataService.selectDictDataById(dataId));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/add", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "新增字典数据v1", description = "新增字典数据")
    @PreAuthorize("@as.hasAuthority('sys:dict:add')")
    public Result<Boolean> add(@Validated(value = SysDictDataCommand.Created.class)
                               @RequestBody SysDictDataCommand com) {
        return Result.status(sysDictDataService.add(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/edit", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "编辑字典数据v1", description = "编辑字典数据")
    @PreAuthorize("@as.hasAuthority('sys:dict:edit')")
    public Result<Boolean> edit(@Validated(value = SysDictDataCommand.Update.class)
                                @RequestBody SysDictDataCommand com) {
        return Result.status(sysDictDataService.edit(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @DeleteMapping(value = "/del", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "删除字典数据v1", description = "删除字典数据")
    @PreAuthorize("@as.hasAuthority('sys:dict:del')")
    public Result<Boolean> del(@Validated @RequestBody IdCmdList ids) {
        return Result.status(sysDictDataService.batchDel(ids));
    }

}
