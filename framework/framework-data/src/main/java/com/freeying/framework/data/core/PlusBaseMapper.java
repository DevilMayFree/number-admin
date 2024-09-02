package com.freeying.framework.data.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 扩展Mybatis-Plus的BaseMapper 支持批量插入
 *
 * @author fx
 */
public interface PlusBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入 仅适用于 mysql
     *
     * @param entityList 实体列表
     * @return 影响行数
     */
    Integer insertBatchSomeColumn(List<T> entityList);
}
