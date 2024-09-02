package com.freeying.framework.cache.core;

import cn.hutool.core.util.ObjectUtil;
import com.freeying.framework.cache.core.model.CachedInvocation;
import com.freeying.framework.cache.utils.CacheHelper;
import com.freeying.framework.cache.utils.ThreadPoolUtils;
import jakarta.annotation.Nonnull;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;


/**
 * AppRedisCache
 * <p>自定义redis缓存</p>
 *
 * @author fx
 **/
public class AppRedisCache extends RedisCache {

    private static final Logger log = LoggerFactory.getLogger(AppRedisCache.class);

    private final RLock lock;

    public AppRedisCache(String name,
                         RedisCacheWriter cacheWriter,
                         RedisCacheConfiguration cacheConfig,
                         RedissonClient redissonClient) {
        super(name, cacheWriter, cacheConfig);
        this.lock = redissonClient.getLock("appRedisLock");
    }

    @Override
    public ValueWrapper get(@Nonnull Object key) {
        ValueWrapper valueWrapper = super.get(key);
        CachedInvocation cachedInvocation = CacheHelper.getCacheManager().getCachedInvocation();
        if (cachedInvocation == null) {
            return valueWrapper;
        }
        long preLoadTimeSecond = cachedInvocation.getMetaData().getPreLoadTimeSecond();
        if (ObjectUtil.isNotEmpty(valueWrapper) && preLoadTimeSecond > 0) {
            String cacheKey = createCacheKey(key);
            RedisTemplate<String, Object> cacheRedisTemplate = CacheHelper.getCacheManager().getCacheRedisTemplate();
            Long ttl = cacheRedisTemplate.getExpire(cacheKey, TimeUnit.SECONDS);
            if (ttl != null && ttl <= preLoadTimeSecond) {
                log.debug("appCache try refreshCache. Key: {}, ttl: {}, preLoadTimeSecond: {}", cacheKey, ttl, preLoadTimeSecond);
                ThreadPoolUtils.execute(this::tryRefreshCache);
            }
        }
        return valueWrapper;
    }

    private void tryRefreshCache() {
        try {
            if (lock.tryLock(5, 10, TimeUnit.MICROSECONDS)) {
                CacheHelper.refreshCache(super.getName());
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }

}
