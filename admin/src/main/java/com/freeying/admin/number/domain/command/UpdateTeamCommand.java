package com.freeying.admin.number.domain.command;

import com.freeying.common.core.entity.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * UpdateTeamCommand
 * <p>号码管理操作对象</p>
 *
 * @author fx
 */
@Schema(description = "分配团队操作对象(v1)")
public class UpdateTeamCommand extends Command {

    @Schema(description = "id列表")
    private List<String> ids;

    @NotBlank
    @Schema(description = "团队")
    private String label;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ids", ids)
                .append("label", label)
                .toString();
    }
}
