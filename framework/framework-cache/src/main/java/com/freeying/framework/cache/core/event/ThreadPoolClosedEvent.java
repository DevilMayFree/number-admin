package com.freeying.framework.cache.core.event;


import com.freeying.framework.cache.utils.ThreadPoolUtils;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolClosedEvent
 * <p>线程池优雅关闭事件</p>
 *
 * @author fx
 */
//@Component
public class ThreadPoolClosedEvent implements ApplicationListener<ContextClosedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolClosedEvent.class);

    @Override
    public void onApplicationEvent(@Nonnull ContextClosedEvent event) {
        while (true) {
            if (ThreadPoolUtils.getTaskExecutor().getActiveCount() <= 0) {
                log.info("cache thread pool is closed");
                break;
            }
            try {
                TimeUnit.MICROSECONDS.sleep(10);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
