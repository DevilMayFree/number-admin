package com.freeying.admin.number.domain.command;

import com.freeying.common.core.entity.Command;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * UpdateRenewCommand
 * <p>批量续费操作对象</p>
 *
 * @author fx
 */
@Schema(description = "批量续费操作对象(v1)")
public class UpdateRenewCommand extends Command {

    @Schema(description = "id列表")
    private List<String> ids;

    @Schema(description = "客户剩余天数")
    private String remainingDays;

    @Schema(description = "卡片剩余天数")
    private String cardRemainingDays;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(String remainingDays) {
        this.remainingDays = remainingDays;
    }

    public String getCardRemainingDays() {
        return cardRemainingDays;
    }

    public void setCardRemainingDays(String cardRemainingDays) {
        this.cardRemainingDays = cardRemainingDays;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ids", ids)
                .append("remainingDays", remainingDays)
                .append("cardRemainingDays", cardRemainingDays)
                .toString();
    }
}
