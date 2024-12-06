package com.freeying.admin.number.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.core.enums.TrueFalseEnum;
import com.freeying.common.webmvc.validation.constraints.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * NumEditBatchCommand
 * <p>号码批量编辑操作对象</p>
 *
 * @author fx
 */
@Schema(description = "号码批量编辑操作对象(v1)")
public class NumEditBatchCommand extends Command {

    @Schema(description = "校验超过剩余超过15天")
    @ValidEnum(enumClass = TrueFalseEnum.class, enumMethod = "isValidValue")
    private String checkOverDays;

    @NotBlank
    @Schema(description = "续费天数  续费客户剩余天数")
    private String remainingDays;

    @Schema(description = "号码列表")
    private List<String> numList;

    public String getCheckOverDays() {
        return checkOverDays;
    }

    public void setCheckOverDays(String checkOverDays) {
        this.checkOverDays = checkOverDays;
    }

    public String getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(String remainingDays) {
        this.remainingDays = remainingDays;
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
                .append("checkOverDays", checkOverDays)
                .append("remainingDays", remainingDays)
                .append("numList", numList)
                .toString();
    }
}
