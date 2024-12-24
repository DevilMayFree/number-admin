package com.freeying.admin.number.domain.dto;

import com.freeying.common.core.entity.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * EditBatchItemDTO
 * <p>批量编辑返回数据-无需续费，带剩余天数</p>
 *
 * @author fx
 */
@Schema(description = "批量编辑返回数据-无需续费，带剩余天数")
public class EditBatchItemDTO extends DTO {

    @Schema(description = "号码")
    private String number;

    @Schema(description = "剩余天数")
    private String days;

    public EditBatchItemDTO() {
        // something
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("number", number)
                .append("days", days)
                .toString();
    }
}
