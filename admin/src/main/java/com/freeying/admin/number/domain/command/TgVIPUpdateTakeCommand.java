package com.freeying.admin.number.domain.command;

import com.freeying.common.core.entity.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * TgVIPUpdateTakeCommand
 * <p>tg会员获取待领取更新操作对象</p>
 *
 * @author fx
 */
@Schema(description = "tg会员获取待领取更新操作对象(v1)")
public class TgVIPUpdateTakeCommand extends Command {

    @Schema(description = "更新id列表")
    private List<String> ids;

    @Schema(description = "变更的状态")
    private Integer status;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ids", ids)
                .append("status", status)
                .toString();
    }
}
