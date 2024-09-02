package com.freeying.framework.cache.service;

import java.util.concurrent.TimeUnit;

/**
 * RedisService
 *
 * @author fx
 */
public interface RedissonService {

    /**
     * 添加延迟队列
     *
     * @param value     队列值
     * @param delay     延迟时间
     * @param timeUnit  时间单位
     * @param queueCode 队列键
     * @param <T>       值对象
     * @return boolean
     */
    <T> boolean addDelayQueue(T value, long delay, TimeUnit timeUnit, String queueCode);

    /**
     * getBlockQueue
     *
     * @param queueCode 队列键
     * @param <T>       值对象
     * @return 值对象
     * @throws InterruptedException 中断异常
     */
    <T> T getBlockQueue(String queueCode) throws InterruptedException;

    /**
     * getDelayQueue
     *
     * @param queueCode 队列键
     * @param <T>       值对象
     * @return 值对象
     * @throws InterruptedException 中断异常
     */
    <T> T getDelayQueue(String queueCode) throws InterruptedException;

    /**
     * 删除指定队列中的消息
     *
     * @param o         指定删除的消息对象队列值(同队列需保证唯一性)
     * @param queueCode 指定队列键
     * @return boolean
     */
    boolean removeDelayedQueue(Object o, String queueCode);

    /**
     * 释放消息队列
     *
     * @param queueCode 指定队列键
     */
    void destroy(String queueCode);
}