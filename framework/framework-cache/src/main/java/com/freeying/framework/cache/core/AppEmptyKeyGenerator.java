package com.freeying.framework.cache.core;

import com.freeying.framework.cache.constants.CacheConstant;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * AppEmptyKeyGenerator
 * <p>空的keyGenerator,用于生成空Key,以empty为标识</p>
 *
 * @author fx
 */
public class AppEmptyKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return CacheConstant.CUSTOM_CACHE_EMPTY_KEY;
    }

}