package com.freeying.admin.number.domain.dto;

import com.freeying.common.core.entity.DTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * NumManagerDTO
 * <p>号码管理列表返回数据</p>
 *
 * @author fx
 */
@Schema(description = "号码管理列表返回数据")
public class NumManagerDTO extends DTO {

    @Schema(description = "主键id")
    private Long id;

    @Schema(description = "号码")
    private String number;

    @Schema(description = "团队")
    private String label;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "客户过期时间")
    private LocalDateTime expiryDate;

    @Schema(description = "客户剩余天数")
    private String remainingDays;

    @Schema(description = "卡片过期时间")
    private LocalDateTime cardExpiryDate;

    @Schema(description = "卡片剩余天数")
    private String cardRemainingDays;

    @Schema(description = "激活时间")
    private LocalDateTime entryDate;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建人")
    private Long createBy;

    @Schema(description = "创建时间")
    private LocalDateTime gmtCreate;

    @Schema(description = "更新人")
    private Long updateBy;

    @Schema(description = "更新时间")
    private LocalDateTime gmtModified;

    public NumManagerDTO() {
        // something
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(String remainingDays) {
        this.remainingDays = remainingDays;
    }

    public LocalDateTime getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(LocalDateTime cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public String getCardRemainingDays() {
        return cardRemainingDays;
    }

    public void setCardRemainingDays(String cardRemainingDays) {
        this.cardRemainingDays = cardRemainingDays;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("number", number)
                .append("label", label)
                .append("code", code)
                .append("expiryDate", expiryDate)
                .append("remainingDays", remainingDays)
                .append("cardExpiryDate", cardExpiryDate)
                .append("cardRemainingDays", cardRemainingDays)
                .append("entryDate", entryDate)
                .append("remark", remark)
                .append("createBy", createBy)
                .append("gmtCreate", gmtCreate)
                .append("updateBy", updateBy)
                .append("gmtModified", gmtModified)
                .toString();
    }
}
