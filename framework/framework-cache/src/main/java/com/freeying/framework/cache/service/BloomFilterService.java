package com.freeying.framework.cache.service;

import java.util.List;

/**
 * BloomFilterService
 *
 * @author fx
 */
public interface BloomFilterService {

    /**
     * reserve
     *
     * @param filterName filterName
     * @param errorRate  错误率，期望错误率越低，需要的空间就越大，默认0.01
     * @param capacity   初始容量，当实际元素的数量超过这个初始化容量时，误判率上升，默认100
     * @return Boolean
     */
    Boolean reserve(String filterName, String errorRate, int capacity);

    /**
     * bloomFilterAdd
     *
     * @param filterName filterName
     * @param value      value
     * @return Boolean
     */
    Boolean add(String filterName, String value);

    /**
     * bloomFilterMAdd
     *
     * @param filterName filterName
     * @param value      value
     * @return Boolean
     */
    List<Long> mAdd(String filterName, String... value);

    /**
     * bloomFilterExists
     *
     * @param filterName filterName
     * @param value      value
     * @return Boolean
     */
    Boolean exists(String filterName, String value);

    /**
     * bloomFilterMExists
     *
     * @param filterName filterName
     * @param value      value
     * @return Boolean
     */
    List<Long> mExists(String filterName, String... value);
}
