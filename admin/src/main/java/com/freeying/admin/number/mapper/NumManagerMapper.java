package com.freeying.admin.number.mapper;

import com.freeying.admin.number.domain.po.NumManager;
import com.freeying.admin.number.domain.query.NumManagerPageQuery;
import com.freeying.common.core.web.PageInfo;
import com.freeying.framework.data.core.PlusBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NumManagerMapper extends PlusBaseMapper<NumManager> {

    /**
     * 号码管理分页查询
     *
     * @param pageInfo 分页对象
     * @param qry      NumManagerPageQuery
     * @return 分页对象
     */
    PageInfo<NumManager> selectNumManagerPage(@Param("page") PageInfo<?> pageInfo, @Param("qry") NumManagerPageQuery qry);

    /**
     * 根据号码查询
     *
     * @param number 号码
     * @return po
     */
    NumManager selectNumManagerByNumber(@Param("number") String number);

    /**
     * 根据编码查询
     *
     * @param code 编码
     * @return po
     */
    NumManager selectNumManagerByCode(@Param("code") String code);
}
