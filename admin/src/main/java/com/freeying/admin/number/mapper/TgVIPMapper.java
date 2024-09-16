package com.freeying.admin.number.mapper;

import com.freeying.admin.number.domain.po.TgVIP;
import com.freeying.admin.number.domain.query.TgVIPPageQuery;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.PlusBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TgVIPMapper extends PlusBaseMapper<TgVIP> {

    /**
     * 未领取分页查询
     *
     * @param pageInfo 分页对象
     * @param qry      TgVIPPageQuery
     * @return 分页对象
     */
    PageInfo<TgVIP> selectTgVIPPage(@Param("page") PageInfo<?> pageInfo, @Param("qry") TgVIPPageQuery qry);

    /**
     * 获取指定数量的内容
     *
     * @param count 数量
     * @return 内容
     */
    List<TgVIP> getTakeContent(@Param("count") Integer count);

    /**
     * 更新状态
     *
     * @param status 状态
     * @return 影响数量
     */
    Integer updateStatus(@Param("status") Integer status, @Param("ids") List<Long> ids);

}
