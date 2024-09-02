package com.freeying.common.core.entity;

import com.freeying.common.core.web.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Query request from Client.
 *
 * @author fx
 */
public abstract class Query<T> extends DTO {

    @Schema(description = "分页查询对象")
    private PageQuery<T> pageQuery = new PageQuery<>();

    public PageQuery<T> getPageQuery() {
        return pageQuery;
    }

    public void setPageQuery(PageQuery<T> pageQuery) {
        this.pageQuery = pageQuery;
    }
}
