package com.freeying.framework.data.core;

import com.freeying.common.core.entity.DTO;
import com.freeying.common.webmvc.validation.constraints.ValidLong;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * IdCmd
 * <p>Id操作对象</p>
 *
 * @author fx
 */
public class IdCmd extends DTO {

    @Schema(description = "id")
    @ValidLong
    private String id;

    @Schema(hidden = true)
    private Long longId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.longId = Long.valueOf(id);
    }

    public Long getLongId() {
        return longId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .toString();
    }
}
