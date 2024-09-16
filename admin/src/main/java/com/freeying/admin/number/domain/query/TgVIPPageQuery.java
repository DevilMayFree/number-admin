package com.freeying.admin.number.domain.query;

import com.freeying.admin.number.domain.dto.TgVIPDTO;
import com.freeying.common.core.entity.Query;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * TgVIPPageQuery
 * <p>VIP未领取分页查询对象</p>
 *
 * @author fx
 */
@Schema(description = "VIP未领取分页查询对象(v1)")
public class TgVIPPageQuery extends Query<TgVIPDTO> {

}
