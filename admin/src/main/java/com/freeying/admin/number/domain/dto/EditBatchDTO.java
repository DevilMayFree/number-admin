package com.freeying.admin.number.domain.dto;

import com.freeying.common.core.entity.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * EditBatchDTO
 * <p>批量编辑返回数据</p>
 *
 * @author fx
 */
@Schema(description = "批量编辑返回数据")
public class EditBatchDTO extends DTO {

    @Schema(description = "批量编辑状态")
    private Integer editStatus;

    @Schema(description = "号码列表")
    private List<String> numList = new ArrayList<>();

    public EditBatchDTO() {
        // something
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
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
                .append("editStatus", editStatus)
                .append("numList", numList)
                .toString();
    }
}
