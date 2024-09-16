package com.freeying.admin.number.domain.command;

import com.freeying.common.core.entity.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * TgVIPAddBatchCommand
 * <p>tg会员批量新增操作对象</p>
 *
 * @author fx
 */
@Schema(description = "tg会员批量新增操作对象(v1)")
public class TgVIPAddBatchCommand extends Command {

    @Schema(description = "内容列表")
    private List<String> contentList;

    public List<String> getContentList() {
        return contentList;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("contentList", contentList)
                .toString();
    }
}
