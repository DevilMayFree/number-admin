package com.freeying.admin.sys.domain.query;

import com.freeying.common.core.entity.Query;
import com.freeying.common.core.enums.StatusEnum;
import com.freeying.common.webmvc.validation.constraints.ValidEnum;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import com.freeying.admin.sys.domain.dto.SysDictDataDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * SysDictDataPageQuery
 * <p>系统字典项数据分页查询对象</p>
 *
 * @author fx
 */
@Schema(description = "系统字典项数据分页查询对象(v1)")
public class SysDictDataPageQuery extends Query<SysDictDataDTO> {

    @Schema(description = "字典类型id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ValidLong
    private String dictId;

    @Schema(description = "关键字(字典项文本/字典项值)")
    private String keywords;

    @Schema(description = "字典项状态")
    @ValidEnum(enableEmpty = true, enumClass = StatusEnum.class, enumMethod = "isValidValue")
    private Integer status;

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dictId", dictId)
                .append("keywords", keywords)
                .append("status", status)
                .toString();
    }
}
