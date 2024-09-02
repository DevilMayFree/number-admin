package com.freeying.framework.cache.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * CacheMetaData
 * <p>缓存信息元数据</p>
 *
 * @author fx
 */
public class CacheMetaData {

    private Object key;
    private String[] cacheNames;
    private long expiredTimeSecond;
    private long preLoadTimeSecond;

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public String[] getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(String[] cacheNames) {
        this.cacheNames = cacheNames;
    }

    public long getExpiredTimeSecond() {
        return expiredTimeSecond;
    }

    public void setExpiredTimeSecond(long expiredTimeSecond) {
        this.expiredTimeSecond = expiredTimeSecond;
    }

    public long getPreLoadTimeSecond() {
        return preLoadTimeSecond;
    }

    public void setPreLoadTimeSecond(long preLoadTimeSecond) {
        this.preLoadTimeSecond = preLoadTimeSecond;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("key", key)
                .append("cacheNames", cacheNames)
                .append("expiredTimeSecond", expiredTimeSecond)
                .append("preLoadTimeSecond", preLoadTimeSecond)
                .toString();
    }
}
