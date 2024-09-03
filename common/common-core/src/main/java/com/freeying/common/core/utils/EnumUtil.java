package com.freeying.common.core.utils;

import java.lang.reflect.Array;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * EnumUtil
 *
 * @author fx
 */
public final class EnumUtil {

    private EnumUtil() {
    }

    public static <T, E extends Enum<E>> Function<T, E> lookupMap(Class<E> clazz, Function<E, T> mapper) {
        @SuppressWarnings("unchecked")
        E[] emptyArray = (E[]) Array.newInstance(clazz, 0);
        return lookupMap(EnumSet.allOf(clazz).toArray(emptyArray), mapper);
    }

    public static <T, E extends Enum<E>> Function<T, E> lookupMap(E[] values, Function<E, T> mapper) {
        Map<T, E> index = HashMap.newHashMap(capacity(values.length));
        for (E value : values) {
            index.put(mapper.apply(value), value);
        }
        return index::get;
    }

    private static int capacity(int expectedSize) {
        // 期望值小于3 则 就期望值+1 返回
        if (expectedSize < 3) {
            return expectedSize + 1;
        } else {
            // 若期望值不小于3，则 先扩大1/4倍
            return expectedSize < 1073741824 ? (int) (expectedSize / 0.75F + 1.0F) : 2147483647;
        }
    }

}
