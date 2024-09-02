package com.freeying.framework.cache.config;

import com.freeying.common.core.utils.JacksonHolder;
import com.freeying.framework.cache.core.serializer.GenericClassJackson2JsonRedisSerializer;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * AppRedisCacheConfigurationHolder
 *
 * @author fx
 */
public final class AppRedisCacheConfigurationHolder {

    private AppRedisCacheConfigurationHolder() {
    }

    /**
     * string序列化
     *
     * @return StringRedisSerializer
     */
    public static StringRedisSerializer getStringRedisSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * genericJackson2JsonRedisSerializer
     *
     * @return StringRedisSerializer
     */
    public static RedisSerializer<Object> getGenericJackson2JsonRedisSerializer() {
        return new GenericClassJackson2JsonRedisSerializer();
    }

    /**
     * jackson2JsonRedisSerializer
     *
     * @return RedisSerializer
     */
    public static RedisSerializer<Object> getJackson2JsonRedisSerializer() {
        // Jackson序列化
        return new Jackson2JsonRedisSerializer<>(JacksonHolder.getInstance(), Object.class);
    }

    /**
     * default RedisCacheConfiguration
     *
     * @return RedisCacheConfiguration
     */
    public static RedisCacheConfiguration getRedisCacheConfiguration() {
        // 分别创建String和JSON格式序列化对象，对缓存数据key和value进行转换
        RedisSerializer<String> stringSerializer = getStringRedisSerializer();
        RedisSerializer<Object> genericJackson2JsonRedisSerializer = getGenericJackson2JsonRedisSerializer();
        // 定制缓存数据序列化方式及时效

        return RedisCacheConfiguration.defaultCacheConfig()
                // 默认设置缓存过期时间为一天
                .entryTtl(Duration.ofDays(1))
                // 分隔改为单冒号
                .computePrefixWith(name -> name + ":")
                // 设置CacheManager的key序列化方式为string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(stringSerializer))
                // 设置CacheManager的value序列化方式为json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(genericJackson2JsonRedisSerializer))
                // 禁用缓存空值，不缓存null校验
                .disableCachingNullValues();
    }
}
