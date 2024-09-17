package com.freeying.admin.number.mapper;

import com.freeying.admin.number.domain.po.TgYear;
import com.freeying.admin.number.domain.query.TgVIPLogPageQuery;
import com.freeying.admin.number.domain.query.TgVIPPageQuery;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.PlusBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TgYearMapper extends PlusBaseMapper<TgYear> {

    /**
     * 未领取分页查询
     *
     * @param pageInfo 分页对象
     * @param qry      TgYearPageQuery
     * @return 分页对象
     */
    PageInfo<TgYear> selectTgYearPage(@Param("page") PageInfo<?> pageInfo, @Param("qry") TgVIPPageQuery qry);

    /**
     * 查询已领取的记录
     *
     * @param pageInfo 分页对象
     * @param qry      TgYearLogPageQuery
     * @param userId   当前用户id
     * @return 分页对象
     */
    PageInfo<TgYear> selectTgYearLogPage(@Param("page") PageInfo<?> pageInfo, @Param("qry") TgVIPLogPageQuery qry, @Param("userId") Long userId);

    /**
     * 获取指定数量的内容
     *
     * @param count 数量
     * @return 内容
     */
    List<TgYear> getTakeContent(@Param("count") Integer count);

    /**
     * 更新状态
     *
     * @param status 状态
     * @return 影响数量
     */
    Integer updateStatus(@Param("status") Integer status, @Param("ids") List<Long> ids);

}
