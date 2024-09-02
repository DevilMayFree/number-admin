package com.freeying.framework.cache.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.freeying.common.core.utils.SpringContextHolder;
import com.freeying.framework.cache.annotation.AppCacheable;
import com.freeying.framework.cache.config.AppRedisCacheConfigurationHolder;
import com.freeying.framework.cache.constants.CacheConstant;
import com.freeying.framework.cache.core.AppRedisCacheManager;
import com.freeying.framework.cache.core.init.CacheExpireTimeInit;
import com.freeying.framework.cache.core.model.CacheMetaData;
import com.freeying.framework.cache.core.model.CachedInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;
import java.util.Map;

/**
 * CacheHelper
 * <p>缓存过期时间以及过期时缓存自动刷新核心类</p>
 *
 * @author fx
 */
public final class CacheHelper {

    private static final Logger log = LoggerFactory.getLogger(CacheHelper.class);

    private CacheHelper() {
    }

    public static AppRedisCacheManager getCacheManager() {
        return SpringContextHolder.getBean(CacheConstant.CUSTOM_CACHE_MANAGER, AppRedisCacheManager.class);
    }


    /**
     * {@link CacheExpireTimeInit}
     *
     * @param appCacheable AppCacheable
     */
    public static void initExpireTime(AppCacheable appCacheable) {

        String[] cacheNames = getCacheNames(appCacheable);

        if (ArrayUtil.isNotEmpty(cacheNames)) {
            Map<String, RedisCacheConfiguration> initialCacheConfigurations = getCacheManager().getInitialCacheConfigurations();
            for (String cacheName : cacheNames) {

                RedisCacheConfiguration redisCacheConfiguration = AppRedisCacheConfigurationHolder.getRedisCacheConfiguration()
                        .entryTtl(Duration.ofSeconds(appCacheable.expiredTimeSecond()));

                initialCacheConfigurations.put(cacheName, redisCacheConfiguration);
            }
        }

    }

    public static void initializeCaches() {
        getCacheManager().initializeCaches();
    }

    public static String[] getCacheNames(AppCacheable appCacheable) {
        String[] cacheNames = appCacheable.cacheNames();
        if (ArrayUtil.isEmpty(cacheNames)) {
            cacheNames = appCacheable.value();
        }
        return cacheNames;
    }

    public static void refreshCache(String cacheName) {
        boolean isMatchCacheName = isMatchCacheName(cacheName);
        if (isMatchCacheName) {
            CachedInvocation cachedInvocation = getCacheManager().getCachedInvocation();
            boolean invocationSuccess = false;
            Object computed = null;
            try {
                computed = cachedInvocation.invoke();
                invocationSuccess = true;
            } catch (Exception ex) {
                log.error("CacheHelper refresh cache fail :{}", ex.getMessage(), ex);
            }

            if (invocationSuccess) {
                Cache cache = getCacheManager().getCache(cacheName);
                if (ObjectUtil.isNotEmpty(cache)) {
                    Object cacheKey = cachedInvocation.getMetaData().getKey();
                    assert cache != null;
                    cache.put(cacheKey, computed);

                    if (log.isDebugEnabled()) {
                        log.debug("CacheHelper refresh cache with cacheName :{}，key :{} finished!", cacheName, cacheKey);
                    }
                }
            }
        }

    }


    private static boolean isMatchCacheName(String cacheName) {
        CachedInvocation cachedInvocation = getCacheManager().getCachedInvocation();
        if (ObjectUtil.isEmpty(cachedInvocation)) {
            log.warn("cachedInvocation is empty");
            return false;
        }
        CacheMetaData metaData = cachedInvocation.getMetaData();
        for (String name : metaData.getCacheNames()) {
            if (name.equals(cacheName)) {
                return true;
            }
        }
        return true;
    }
}
