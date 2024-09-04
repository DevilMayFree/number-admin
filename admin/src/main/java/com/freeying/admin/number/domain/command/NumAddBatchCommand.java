package com.freeying.admin.number.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * NumAddBatchCommand
 * <p>号码批量新增操作对象</p>
 *
 * @author fx
 */
@Schema(description = "号码批量新增操作对象(v1)")
public class NumAddBatchCommand extends Command {

    @NotBlank
    @Schema(description = "该批次第一个编码")
    private String firstCode;

    @Schema(description = "号码列表")
    private List<String> numList;

    public String getFirstCode() {
        return firstCode;
    }

    public void setFirstCode(String firstCode) {
        this.firstCode = firstCode;
    }

    public List<String> getNumList() {
        return numList;
    }

    public void setNumList(List<String> numList) {
        this.numList = numList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstCode", firstCode)
                .append("numList", numList)
                .toString();
    }
}
