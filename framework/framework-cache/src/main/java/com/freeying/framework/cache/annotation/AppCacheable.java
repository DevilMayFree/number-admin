package com.freeying.framework.cache.annotation;

import com.freeying.framework.cache.constants.CacheConstant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Cacheable
 * <p>自定义cacheable注解,添加过期时间</p>
 *
 * @author fx
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Cacheable(cacheManager = CacheConstant.CUSTOM_CACHE_MANAGER, keyGenerator = CacheConstant.CUSTOM_CACHE_KEY_GENERATOR)
public @interface AppCacheable {

    @AliasFor(annotation = Cacheable.class, attribute = "value")
    String[] value() default {};

    @AliasFor(annotation = Cacheable.class, attribute = "cacheNames")
    String[] cacheNames() default {};

    @AliasFor(annotation = Cacheable.class, attribute = "key")
    String key() default "";

    @AliasFor(annotation = Cacheable.class, attribute = "keyGenerator")
    String keyGenerator() default "";

    @AliasFor(annotation = Cacheable.class, attribute = "cacheResolver")
    String cacheResolver() default "";

    @AliasFor(annotation = Cacheable.class, attribute = "condition")
    String condition() default "";

    @AliasFor(annotation = Cacheable.class, attribute = "unless")
    String unless() default "";

    @AliasFor(annotation = Cacheable.class, attribute = "sync")
    boolean sync() default false;

    /**
     * 缓存过期时间
     *
     * @return expiredTimeSecond
     */
    long expiredTimeSecond() default 0;

    /**
     * 缓存自动刷新时间
     *
     * @return expiredTimeSecond
     */
    long preLoadTimeSecond() default 0;

}
