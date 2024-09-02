package com.freeying.framework.cache.core;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;

import java.lang.reflect.Method;

import static com.freeying.framework.cache.constants.CacheConstant.CUSTOM_CACHE_EMPTY_KEY;

/**
 * AppSimpleKeyGenerator
 * <p>简易key生成,参数为空则使用empty字符,参数不为空则同SimpleKeyGenerator</p>
 *
 * @author fx
 */
public class AppSimpleKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return generateKey(params);
    }

    /**
     * Generate a key based on the specified parameters.
     */
    public static Object generateKey(Object... params) {
        if (params.length == 0) {
            return CUSTOM_CACHE_EMPTY_KEY;
        }
        if (params.length == 1) {
            Object param = params[0];
            if (param != null && !param.getClass().isArray()) {
                return param;
            }
        }
        return new SimpleKey(params);
    }

}