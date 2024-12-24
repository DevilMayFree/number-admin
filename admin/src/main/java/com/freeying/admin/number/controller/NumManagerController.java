package com.freeying.admin.number.controller;

import com.freeying.admin.number.domain.command.*;
import com.freeying.admin.number.domain.dto.DoEditBatchDTO;
import com.freeying.admin.number.domain.dto.EditBatchDTO;
import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.admin.number.domain.query.NumManagerExportQuery;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.admin.number.service.NumManagerService;
import com.freeying.admin.number.service.impl.ScheduledTask;
import com.freeying.admin.number.support.poi.ExcelUtil;
import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.web.PageInfo;
import com.freeying.common.core.web.Result;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import com.freeying.framework.data.core.IdCmdList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/num/manager")
@Tag(description = "numManager", name = "号码管理模块")
public class NumManagerController {
    private final NumManagerService numManagerService;
    private final ScheduledTask scheduledTask;

    public NumManagerController(NumManagerService numManagerService, ScheduledTask scheduledTask) {
        this.numManagerService = numManagerService;
        this.scheduledTask = scheduledTask;
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

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/export")
    @Operation(summary = "导出号码v1", description = "导出号码")
    @PreAuthorize("@as.hasAuthority('num:manager:export')")
    public void export(HttpServletResponse response, NumManagerExportQuery qry) {
        List<NumManagerDTO> list = numManagerService.export(qry);
        ExcelUtil<NumManagerDTO> util = new ExcelUtil<>(NumManagerDTO.class);
        util.exportExcel(response, list, "sheet1");
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/updateTeam", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分配团队v1", description = "分配团队")
    @PreAuthorize("@as.hasAuthority('num:manager:edit')")
    public Result<Boolean> updateTeam(@Validated @RequestBody UpdateTeamCommand com) {
        return Result.status(numManagerService.updateTeam(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PutMapping(value = "/updateRenew", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "批量续费v1", description = "批量续费")
    @PreAuthorize("@as.hasAuthority('num:manager:edit')")
    public Result<Boolean> updateRenew(@Validated @RequestBody UpdateRenewCommand com) {
        if (StringUtils.isBlank(com.getRemainingDays())) {
            com.setRemainingDays("0");
        }
        if (StringUtils.isBlank(com.getCardRemainingDays())) {
            com.setCardRemainingDays("0");
        }
        try {
            Long.parseLong(com.getRemainingDays());
            Long.parseLong(com.getCardRemainingDays());
        } catch (Exception e) {
            return Result.error("请输入数值");
        }
        return Result.status(numManagerService.updateRenew(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/addBatch", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "批量新增v1", description = "批量新增")
    @PreAuthorize("@as.hasAuthority('num:manager:add')")
    public Result<Boolean> addBatch(@Validated @RequestBody NumAddBatchCommand com) {
        return Result.status(numManagerService.addBatch(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/editBatch", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "批量编辑v1", description = "批量编辑")
    @PreAuthorize("@as.hasAuthority('num:manager:edit')")
    public Result<EditBatchDTO> editBatch(@Validated @RequestBody NumEditBatchCommand com) {
        return Result.success(numManagerService.editBatch(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/doEditBatch", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "执行批量编辑v1", description = "执行批量编辑")
    @PreAuthorize("@as.hasAuthority('num:manager:edit')")
    public Result<DoEditBatchDTO> doEditBatch(@Validated @RequestBody NumEditBatchCommand com) {
        return Result.success(numManagerService.doEditBatch(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/runTask", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "运行更新任务v1", description = "运行更新任务")
    public Result<Boolean> runTask() {
        scheduledTask.scheduledTask();
        return Result.success("update complete");
    }

    @ApiVersion(ApiVersionConstants.V1)
    @GetMapping(value = "/allRenew", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "全部批量续费v1", description = "全部批量续费")
    public Result<Boolean> allRenew() {
        List<NumManagerDTO> list = numManagerService.export(new NumManagerExportQuery());
        List<String> ids = list.stream().map(i -> String.valueOf(i.getId())).toList();

        UpdateRenewCommand com = new UpdateRenewCommand();
        com.setIds(ids);
        com.setRemainingDays("0");
        com.setCardRemainingDays("0");
        boolean b = numManagerService.updateRenew(com);
        return Result.status(b);
    }

}
