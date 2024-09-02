package com.freeying.admin.sys.domain.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * SysDictDataQuery
 * <p>系统字典项数据详情查询对象</p>
 *
 * @author fx
 */
@Schema(description = "系统字典项数据详情查询对象(v1)")
public class SysDictDataQuery {

    @Schema(description = "字典类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
