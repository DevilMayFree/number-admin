package com.freeying.framework.cache.service.impl;

import com.freeying.framework.cache.service.RedissonService;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * RedissonServiceImpl
 * <p>Redisson操作封装</p>
 *
 * @author fx
 */
public class RedissonServiceImpl implements RedissonService {
    private static final Logger log = LoggerFactory.getLogger(RedissonServiceImpl.class);

    private final RedissonClient redissonClient;

    public RedissonServiceImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public <T> boolean addDelayQueue(T value, long delay, TimeUnit timeUnit, String queueCode) {
        if (StringUtils.isBlank(queueCode) || Objects.isNull(value)) {
            return false;
        }
        try {
            RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(queueCode);
            RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
            delayedQueue.offer(value, delay, timeUnit);
            if (log.isDebugEnabled()) {
                log.debug("(添加延时队列成功) 队列键：{}，队列值：{}，延迟时间：{}", queueCode, value, timeUnit.toSeconds(delay) + "秒");
            }
        } catch (Exception e) {
            log.error("(添加延时队列失败) {}", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public <T> T getBlockQueue(String queueCode) throws InterruptedException {
        if (StringUtils.isBlank(queueCode)) {
            return null;
        }
        RBlockingDeque<T> blockingDeque = redissonClient.getBlockingDeque(queueCode);
        return blockingDeque.poll();
    }

    @Override
    public <T> T getDelayQueue(String queueCode) throws InterruptedException {
        if (StringUtils.isBlank(queueCode)) {
            return null;
        }
        RBlockingDeque<T> blockingDeque = redissonClient.getBlockingDeque(queueCode);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        return delayedQueue.poll();
    }

    @Override
    public boolean removeDelayedQueue(Object o, String queueCode) {
        if (StringUtils.isBlank(queueCode) || Objects.isNull(o)) {
            return false;
        }
        RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(queueCode);
        RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        return delayedQueue.remove(o);
    }

    @Override
    public void destroy(String queueCode) {
        RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(queueCode);
        RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
        delayedQueue.destroy();
    }

}
