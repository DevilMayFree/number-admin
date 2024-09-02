package com.freeying.common.core.utils;

import com.freeying.common.core.entity.Converter;
import com.freeying.common.core.web.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页工具
 * <p>数据转换,用于po转dto</p>
 *
 * @author fx
 */
public final class DataConverter {

    private DataConverter() {

    }

    /**
     * list转换为其他泛型
     *
     * @param assembler assembler
     * @param list      list
     * @return List
     */
    public static <T, E> List<E> converter(Function<T, E> assembler, List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(assembler).toList();
    }

    /**
     * 获取分页后list里面的Page对象,并转换为其他泛型
     *
     * @param assembler assembler
     * @param pageInfo  pageInfo
     * @return PageInfo
     */
    @SuppressWarnings({"unchecked"})
    public static <T, E> PageInfo<E> converter(Converter<T, E> assembler, PageInfo<T> pageInfo) {
        if (pageInfo == null) {
            return null;
        }
        if (CollectionUtils.isEmpty(pageInfo.getRecords())) {
            pageInfo.setRecords(Lists.newArrayList());
            return (PageInfo<E>) pageInfo;
        }
        PageInfo<E> page = (PageInfo<E>) pageInfo;
        page.setRecords(converter(assembler, pageInfo.getRecords()));
        return page;
    }

    /**
     * listToMap stream转换
     *
     * @param collection collection
     * @param keyMapper  keyMapper
     * @param <T>        key类型
     * @param <E>        转换后value类型
     * @return Map
     */
    public static <T, E> Map<T, E> listToMap(Collection<E> collection, Function<E, T> keyMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return new HashMap<>();
        }
        return collection.stream().collect(Collectors.toMap(keyMapper, a -> a, (k1, k2) -> k1));
    }
}
