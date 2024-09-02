package com.freeying.common.core.web;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * PageQuery
 * <p>
 * 当前对象区分于PageInfo
 * 作为Swagger显示的入参信息
 * 分页请求入参使用PageQuery
 * 返回使用PageInfo
 * </p>
 *
 * @author fx
 */
@Schema(description = "分页查询对象")
public class PageQuery<T> extends PageInfo<T> {
}
