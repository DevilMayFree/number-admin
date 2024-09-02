package com.freeying.framework.cache.service.impl;

import com.freeying.framework.cache.service.BloomFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * BloomFilterServiceImpl
 * <p>BloomFilter封装,需要redis服务支持</p>
 *
 * @author fx
 */
public class BloomFilterServiceImpl implements BloomFilterService {
    private static final Logger log = LoggerFactory.getLogger(BloomFilterServiceImpl.class);

    /**
     * 布隆过滤器初始化脚本
     */
    private static final String BLOOM_CREATE_SCRIPT = "return redis.call('BF.RESERVE', KEYS[1], KEYS[2], KEYS[3])";

    /**
     * 布隆过滤器添加脚本
     */
    private static final String BLOOM_ADD_SCRIPT = "return redis.call('BF.ADD', KEYS[1], KEYS[2])";

    /**
     * 布隆过滤器值存在判断脚本
     */
    private static final String BLOOM_EXISTS_SCRIPT = "return redis.call('BF.EXISTS', KEYS[1], KEYS[2])";

    private final RedisTemplate<String, Object> redisTemplate;

    public BloomFilterServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean reserve(String filterName, String errorRate, int capacity) {
        log.debug("bloomFilter reserve");
        DefaultRedisScript<Boolean> bloomCreate = new DefaultRedisScript<>(BLOOM_CREATE_SCRIPT);
        bloomCreate.setResultType(Boolean.class);
        return redisTemplate.execute(bloomCreate, Arrays.asList(filterName, errorRate, String.valueOf(capacity)), (Object) null);
    }

    @Override
    public Boolean add(String filterName, String value) {
        log.debug("bloomFilterAdd");
        DefaultRedisScript<Boolean> bloomAdd = new DefaultRedisScript<>(BLOOM_ADD_SCRIPT);
        bloomAdd.setResultType(Boolean.class);
        return redisTemplate.execute(bloomAdd, Arrays.asList(filterName, value), (Object) null);
    }

    @Override
    public List<Long> mAdd(String filterName, String... value) {
        log.debug("bloomFilterMAdd");
        DefaultRedisScript<List<Long>> add = new DefaultRedisScript<>(getBloomScript("BF.MADD", value.length));
        add.setResultType(getResultType());
        List<String> keys = new ArrayList<>();
        keys.add(filterName);
        keys.addAll(Arrays.asList(value));
        return redisTemplate.execute(add, keys, (Object) null);
    }

    @Override
    public Boolean exists(String filterName, String value) {
        log.debug("bloomFilterExists");
        DefaultRedisScript<Boolean> bloomExists = new DefaultRedisScript<>(BLOOM_EXISTS_SCRIPT);
        bloomExists.setResultType(Boolean.class);
        return redisTemplate.execute(bloomExists, Arrays.asList(filterName, value), (Object) null);
    }

    @Override
    public List<Long> mExists(String filterName, String... value) {
        log.debug("bloomFilterMExists");
        DefaultRedisScript<List<Long>> exists = new DefaultRedisScript<>(getBloomScript("BF.MEXISTS", value.length));
        exists.setResultType(getResultType());
        List<String> keys = new ArrayList<>();
        keys.add(filterName);
        keys.addAll(Arrays.asList(value));
        return redisTemplate.execute(exists, keys, (Object) null);
    }


    /**
     * getBloomScript
     *
     * @param command BF.ADD or BF.EXISTS
     * @param len     元素个数
     * @return String
     */
    private String getBloomScript(String command, int len) {
        String rawScript = "return redis.call('" + command + "',KEYS[1],KEYS[2])";
        if (len <= 1) {
            return rawScript;
        }
        StringBuilder str = new StringBuilder("return redis.call('" + command + "',KEYS[1],");

        for (int i = 0; i < len; i++) {
            str.append("KEYS[").append(2 + i).append("]");
            if (i == len - 1) {
                str.append(")");
            } else {
                str.append(",");
            }
        }
        return str.toString();
    }

    /**
     * getResultType
     *
     * @return Class
     */
    @SuppressWarnings("unchecked")
    private Class<List<Long>> getResultType() {
        return (Class<List<Long>>) Collections.<Long>emptyList().getClass();
    }
}
