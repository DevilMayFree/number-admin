package com.freeying.admin.number.controller;

import com.freeying.admin.number.domain.command.TgVIPAddBatchCommand;
import com.freeying.admin.number.domain.command.TgVIPTakeCommand;
import com.freeying.admin.number.domain.command.TgVIPUpdateTakeCommand;
import com.freeying.admin.number.domain.dto.TgVIPDTO;
import com.freeying.admin.number.domain.query.TgVIPLogPageQuery;
import com.freeying.admin.number.domain.query.TgVIPPageQuery;
import com.freeying.admin.number.service.GmailVIPService;
import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.web.PageInfo;
import com.freeying.common.core.web.Result;
import com.freeying.common.core.web.ResultCode;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gmail/vip")
@Tag(description = "GmailVIP", name = "Gmail会员")
public class GmailVIPController {

    private final GmailVIPService gmailVIPService;

    public GmailVIPController(GmailVIPService gmailVIPService) {
        this.gmailVIPService = gmailVIPService;
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询Gmail普通会员列表v1", description = "分页查询Gmail普通会员列表")
    @PreAuthorize("@as.hasAuthority('gmail:vip:list')")
    public Result<PageInfo<TgVIPDTO>> page(@Validated @RequestBody TgVIPPageQuery qry) {
        PageInfo<TgVIPDTO> page = gmailVIPService.page(qry);
        return Result.success(page);
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/addBatch", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "批量新增v1", description = "批量新增")
    @PreAuthorize("@as.hasAuthority('gmail:vip:add')")
    public Result<Boolean> addBatch(@Validated @RequestBody TgVIPAddBatchCommand com) {
        return Result.status(gmailVIPService.addBatch(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/getTakeContent", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "获取指定数量的待领取内容v1", description = "获取指定数量的待领取内容")
    @PreAuthorize("@as.hasAuthority('gmail:vip:list')")
    public Result<List<TgVIPDTO>> getTakeContent(@Validated @RequestBody TgVIPTakeCommand com) {
        return Result.success(gmailVIPService.getTakeContent(com), ResultCode.SUCCESS.getMessage());
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/updateTake", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "更新内容状态v1", description = "更新内容状态")
    @PreAuthorize("@as.hasAuthority('gmail:vip:list')")
    public Result<List<TgVIPDTO>> updateTake(@Validated @RequestBody TgVIPUpdateTakeCommand com) {
        return Result.status(gmailVIPService.updateTakeStatus(com));
    }

    @ApiVersion(ApiVersionConstants.V1)
    @PostMapping(value = "/log/page", produces = HttpConstants.APPLICATION_JSON_UTF8_VALUE)
    @Operation(summary = "分页查询Gmail普通会员列表v1", description = "分页查询Gmail普通会员列表")
    @PreAuthorize("@as.hasAuthority('gmail:vip:list')")
    public Result<PageInfo<TgVIPDTO>> logPage(@Validated @RequestBody TgVIPLogPageQuery qry) {
        PageInfo<TgVIPDTO> page = gmailVIPService.logPage(qry);
        return Result.success(page);
    }

}
