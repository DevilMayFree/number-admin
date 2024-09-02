package com.freeying.framework.data.core;

import com.freeying.common.core.entity.DTO;
import com.freeying.common.webmvc.validation.constraints.ValidIdList;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * IdCmd
 * <p>Id操作对象</p>
 *
 * @author fx
 */
public class IdCmdList extends DTO {

    @Schema(description = "id")
    @ValidIdList
    private List<String> ids;

    @Schema(hidden = true)
    private List<Long> longIds = new ArrayList<>();

    public List<String> getIds() {
        return ids;
    }

    public List<Long> getLongIds() {
        return longIds;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
        ids.forEach(id -> longIds.add(Long.valueOf(id)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ids", ids)
                .toString();
    }
}
