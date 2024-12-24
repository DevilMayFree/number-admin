package com.freeying.admin.number.domain.dto;

import com.freeying.common.core.entity.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * DoEditBatchDTO
 * <p>确定批量编辑返回数据</p>
 *
 * @author fx
 */
@Schema(description = "确定批量编辑返回数据")
public class DoEditBatchDTO extends DTO {

    @Schema(description = "批量编辑状态")
    private Integer editStatus;

    @Schema(description = "续费成功列表号码")
    private List<String> renewSuccessList = new ArrayList<>();

    @Schema(description = "不是我们后台数据的列表号码")
    private List<String> noOurList = new ArrayList<>();

    @Schema(description = "无需续费列表，带剩余天数")
    private List<EditBatchItemDTO> noNeedList = new ArrayList<>();

    public DoEditBatchDTO() {
        // something
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public List<String> getRenewSuccessList() {
        return renewSuccessList;
    }

    public void setRenewSuccessList(List<String> renewSuccessList) {
        this.renewSuccessList = renewSuccessList;
    }

    public List<String> getNoOurList() {
        return noOurList;
    }

    public void setNoOurList(List<String> noOurList) {
        this.noOurList = noOurList;
    }

    public List<EditBatchItemDTO> getNoNeedList() {
        return noNeedList;
    }

    public void setNoNeedList(List<EditBatchItemDTO> noNeedList) {
        this.noNeedList = noNeedList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("editStatus", editStatus)
                .append("renewSuccessList", renewSuccessList)
                .append("noOurList", noOurList)
                .append("noNeedList", noNeedList)
                .toString();
    }
}
