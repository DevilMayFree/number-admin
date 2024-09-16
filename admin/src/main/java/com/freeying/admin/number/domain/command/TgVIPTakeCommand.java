package com.freeying.admin.number.domain.command;

import com.freeying.common.core.entity.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * TgVIPTakeCommand
 * <p>tg会员获取待领取操作对象</p>
 *
 * @author fx
 */
@Schema(description = "tg会员获取待领取操作对象(v1)")
public class TgVIPTakeCommand extends Command {

    @Schema(description = "领取数量")
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("count", count)
                .toString();
    }
}
