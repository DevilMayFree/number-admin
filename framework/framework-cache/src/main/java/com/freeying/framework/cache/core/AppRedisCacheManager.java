package com.freeying.framework.cache.core;

import com.freeying.framework.cache.core.model.CachedInvocation;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.redisson.api.RedissonClient;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * AppRedisCacheManager
 * <p>Custom Redis Cache Manager</p>
 * <p>自定义的redis缓存管理器：支持方法上配置过期时间；支持热加载缓存：缓存即将过期时主动刷新缓存</p>
 *
 * @author fx
 */
public class AppRedisCacheManager extends RedisCacheManager {

    private final Map<String, RedisCacheConfiguration> initialCacheConfigurations;

    private final RedisTemplate<String, Object> cacheRedisTemplate;

    private final RedissonClient redissonClient;

    private final RedisCacheWriter cacheWriter;

    private final RedisCacheConfiguration defaultCacheConfiguration;

    protected CachedInvocation cachedInvocation;

    public AppRedisCacheManager(RedisCacheWriter cacheWriter,
                                RedisCacheConfiguration defaultCacheConfiguration,
                                Map<String, RedisCacheConfiguration> initialCacheConfigurations,
                                RedisTemplate<String, Object> cacheRedisTemplate,
                                RedissonClient redissonClient) {
        super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        this.initialCacheConfigurations = initialCacheConfigurations;
        this.cacheRedisTemplate = cacheRedisTemplate;
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfiguration = defaultCacheConfiguration;
        this.redissonClient = redissonClient;
    }

    @Override
    protected Collection<RedisCache> loadCaches() {
        List<RedisCache> caches = new LinkedList<>();

        for (Map.Entry<String, RedisCacheConfiguration> entry : getInitialCacheConfigurations().entrySet()) {
            caches.add(createRedisCache(entry.getKey(), entry.getValue()));
        }
        return caches;
    }

    //@Subscribe
    @EventListener
    private void doWithCachedInvocationEvent(CachedInvocation cachedInvocation) {
        this.cachedInvocation = cachedInvocation;
    }

    @Override
    public RedisCache createRedisCache(@Nonnull String name, @Nullable RedisCacheConfiguration cacheConfig) {
        return new AppRedisCache(name, cacheWriter, cacheConfig != null ? cacheConfig : defaultCacheConfiguration, redissonClient);
    }

    public Map<String, RedisCacheConfiguration> getInitialCacheConfigurations() {
        return initialCacheConfigurations;
    }

    public CachedInvocation getCachedInvocation() {
        return cachedInvocation;
    }

    public RedisTemplate<String, Object> getCacheRedisTemplate() {
        return cacheRedisTemplate;
    }
}
