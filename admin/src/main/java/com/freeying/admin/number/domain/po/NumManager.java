package com.freeying.admin.number.domain.po;

import com.freeying.framework.data.core.BasePO;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

public class NumManager extends BasePO {

    /**
     * 号码
     */
    private String number;

    /**
     * 团队
     */
    private String label;

    /**
     * 编码
     */
    private String code;

    /**
     * 客户过期时间
     */
    private LocalDateTime expiryDate;

    /**
     * 客户剩余天数
     */
    private Long remainingDays;

    /**
     * 卡片过期时间
     */
    private LocalDateTime cardExpiryDate;

    /**
     * 卡片剩余天数
     */
    private Long cardRemainingDays;

    /**
     * 激活时间
     */
    private LocalDateTime entryDate;

    /**
     * 备注
     */
    private String remark;

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

    public Long getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(Long remainingDays) {
        this.remainingDays = remainingDays;
    }

    public LocalDateTime getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(LocalDateTime cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public Long getCardRemainingDays() {
        return cardRemainingDays;
    }

    public void setCardRemainingDays(Long cardRemainingDays) {
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
