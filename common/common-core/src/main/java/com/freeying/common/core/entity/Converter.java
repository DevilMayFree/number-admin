package com.freeying.common.core.entity;

import java.util.function.Function;

/**
 * Converter interface
 *
 * @author fx
 */
public interface Converter<T, R> extends Function<T, R> {

    /**
     * apply
     *
     * @param t t
     * @return R
     */
    @Override
    R apply(T t);
}
