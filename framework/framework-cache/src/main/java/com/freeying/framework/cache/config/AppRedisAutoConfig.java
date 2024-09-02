package com.freeying.framework.cache.config;

import com.freeying.framework.cache.constants.CacheConstant;
import com.freeying.framework.cache.core.AppEmptyKeyGenerator;
import com.freeying.framework.cache.core.AppRedisCacheManager;
import com.freeying.framework.cache.core.AppSimpleKeyGenerator;
import com.freeying.framework.cache.core.event.ThreadPoolClosedEvent;
import com.freeying.framework.cache.service.BloomFilterService;
import com.freeying.framework.cache.service.RedisService;
import com.freeying.framework.cache.service.impl.BloomFilterServiceImpl;
import com.freeying.framework.cache.service.impl.RedisServiceImpl;
import com.freeying.framework.cache.service.impl.RedissonServiceImpl;
import org.redisson.api.RedissonClient;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class AppRedisAutoConfig {

    private final RedissonClient redissonClient;

    public AppRedisAutoConfig(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Bean
    public ThreadPoolClosedEvent threadPoolClosedEvent() {
        return new ThreadPoolClosedEvent();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        RedisSerializer<Object> jackson2JsonRedisSerializer = AppRedisCacheConfigurationHolder.getGenericJackson2JsonRedisSerializer();
        StringRedisSerializer stringRedisSerializer = AppRedisCacheConfigurationHolder.getStringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return AppRedisCacheConfigurationHolder.getRedisCacheConfiguration();
    }

    @Bean(CacheConstant.CUSTOM_CACHE_MANAGER)
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory, RedisTemplate<String, Object> redisTemplate) {

        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        Map<String, RedisCacheConfiguration> initialCacheConfiguration = new HashMap<>();

        return new AppRedisCacheManager(
                redisCacheWriter,
                redisCacheConfiguration(),
                initialCacheConfiguration,
                redisTemplate,
                redissonClient);
    }

    @Bean(CacheConstant.CUSTOM_CACHE_KEY_GENERATOR)
    public KeyGenerator keyGenerator() {
        return new AppSimpleKeyGenerator();
    }

    @Bean(CacheConstant.EMPTY_CACHE_KEY_GENERATOR)
    public KeyGenerator emptyKeyGenerator() {
        return new AppEmptyKeyGenerator();
    }

    @Bean
    public RedisService redisService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisServiceImpl(redisTemplate);
    }

    @Bean
    public RedissonServiceImpl redissonService(RedissonClient redissonClient) {
        return new RedissonServiceImpl(redissonClient);
    }

    @Bean
    public BloomFilterService bloomFilterService(RedisTemplate<String, Object> redisTemplate) {
        return new BloomFilterServiceImpl(redisTemplate);
    }

}
