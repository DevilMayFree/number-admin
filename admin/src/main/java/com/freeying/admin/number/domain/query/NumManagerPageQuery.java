package com.freeying.admin.number.domain.query;

import com.freeying.admin.number.domain.dto.NumManagerDTO;
import com.freeying.common.core.entity.LocalDateTimeRange;
import com.freeying.common.core.entity.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * NumManagerPageQuery
 * <p>号码管理分页查询对象</p>
 *
 * @author fx
 */
@Schema(description = "号码管理分页查询对象(v1)")
public class NumManagerPageQuery extends Query<NumManagerDTO> {

    @Schema(description = "关键字(号码)")
    private String keywords;

    @Schema(description = "激活时间范围")
    private LocalDateTimeRange entryDate;

    @Schema(description = "客户过期时间天数")
    private String expiryDateNum;

    @Schema(description = "卡片过期时间天数")
    private String cardExpiryDateNum;

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
    }

    public String getCardExpiryDateNum() {
        return cardExpiryDateNum;
    }

    public void setCardExpiryDateNum(String cardExpiryDateNum) {
        this.cardExpiryDateNum = cardExpiryDateNum;
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
