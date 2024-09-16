package com.freeying.admin.number.domain.query;

import com.freeying.admin.number.domain.dto.TgVIPDTO;
import com.freeying.common.core.entity.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * TgVIPLogPageQuery
 * <p>VIP已领取日志分页查询对象</p>
 *
 * @author fx
 */
@Schema(description = "VIP已领取日志分页查询对象(v1)")
public class TgVIPLogPageQuery extends Query<TgVIPDTO> {

}
