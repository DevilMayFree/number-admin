package com.freeying.admin.number.domain.query;

import com.freeying.common.core.entity.DTO;
import com.freeying.common.core.entity.LocalDateTimeRange;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * NumManagerExportQuery
 * <p>号码管理导出对象</p>
 *
 * @author fx
 */
@Schema(description = "号码管理导出对象(v1)")
public class NumManagerExportQuery extends DTO {

    @Schema(description = "关键字(号码)")
    private String keywords;

    @Schema(description = "激活时间范围")
    private LocalDateTimeRange entryDate;

    @Schema(description = "客户过期时间天数")
    private String expiryDateNum;

    private Long longExpiryDateNum;

    @Schema(description = "卡片过期时间天数")
    private String cardExpiryDateNum;

    private Long longCardExpiryDateNum;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public LocalDateTimeRange getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTimeRange entryDate) {
        this.entryDate = entryDate;
    }

    public String getExpiryDateNum() {
        return expiryDateNum;
    }

    public void setExpiryDateNum(String expiryDateNum) {
        this.expiryDateNum = expiryDateNum;
        if (StringUtils.isNotBlank(expiryDateNum)) {
            try {
                this.longExpiryDateNum = Long.parseLong(expiryDateNum);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("请输入有效数值查询");
            }
        }
    }

    public String getCardExpiryDateNum() {
        return cardExpiryDateNum;
    }

    public void setCardExpiryDateNum(String cardExpiryDateNum) {
        this.cardExpiryDateNum = cardExpiryDateNum;
        if (StringUtils.isNotBlank(cardExpiryDateNum)) {
            try {
                this.longCardExpiryDateNum = Long.parseLong(cardExpiryDateNum);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("请输入有效数值查询");
            }
        }
    }

    public Long getLongExpiryDateNum() {
        return longExpiryDateNum;
    }

    public Long getLongCardExpiryDateNum() {
        return longCardExpiryDateNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("keywords", keywords)
                .append("entryDate", entryDate)
                .append("expiryDateNum", expiryDateNum)
                .append("cardExpiryDateNum", cardExpiryDateNum)
                .toString();
    }

}
