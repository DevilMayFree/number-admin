package com.freeying.admin.number.controller;

import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.admin.number.service.NumManagerService;
import com.freeying.common.core.constant.ApiVersionConstants;
import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.web.PageInfo;
import com.freeying.common.core.web.Result;
import com.freeying.common.webmvc.request.annotation.ApiVersion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
