package com.freeying.admin.number.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

/**
 * NumManagerCommand
 * <p>号码管理操作对象</p>
 *
 * @author fx
 */
@Schema(description = "号码管理操作对象(v1)")
public class NumManagerCommand extends Command {

    @ValidLong(groups = {NumManagerCommand.Update.class})
    @Schema(description = "id")
    private String id;

    @NotBlank
    @Schema(description = "号码")
    private String number;

    @NotBlank
    @Schema(description = "团队")
    private String label;

    @NotBlank
    @Schema(description = "编码")
    private String code;

    @Schema(description = "客户过期时间")
    private LocalDateTime expiryDate;

    @ValidLong
    @Schema(description = "客户剩余天数")
    private String remainingDays;

    @Schema(description = "卡片过期时间")
    private LocalDateTime cardExpiryDate;

    @ValidLong
    @Schema(description = "卡片剩余天数")
    private String cardRemainingDays;

    @Schema(description = "激活时间")
    private LocalDateTime entryDate;

    @Schema(description = "备注")
    private String remark;

    public @interface Created {
    }

    public @interface Update {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
                .toString();
    }

}
