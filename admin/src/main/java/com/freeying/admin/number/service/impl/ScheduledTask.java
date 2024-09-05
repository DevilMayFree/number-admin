package com.freeying.admin.number.service.impl;

import com.freeying.admin.number.domain.po.NumManager;
import com.freeying.admin.number.domain.query.NumManagerExportQuery;
import com.freeying.admin.number.mapper.NumManagerMapper;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    private final NumManagerMapper numManagerMapper;

    public ScheduledTask(NumManagerMapper numManagerMapper) {
        this.numManagerMapper = numManagerMapper;
    }

    @Scheduled(cron = "0 10 0 * * ?")
    // @Scheduled(fixedDelay = 5000)
    public void scheduledTask() {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        LocalDateTime now = LocalDateTime.now();

        log.warn("定时任务开始执行,开始时间:{}", now);

        List<NumManager> list = numManagerMapper.selectNumManagerExport(new NumManagerExportQuery());

        int num = 0;
        for (NumManager numManager : list) {
            boolean updateExpiryDate = false;
            boolean updateCardExpiryDate = false;

            LocalDateTime expiryDate = numManager.getExpiryDate();
            LocalDateTime cardExpiryDate = numManager.getCardExpiryDate();
            if (expiryDate != null) {
                if (expiryDate.isAfter(now)) {
                    long day = ChronoUnit.DAYS.between(now, expiryDate);
                    if (day >= 0) {
                        numManager.setRemainingDays(String.valueOf(day));
                    }
                } else {
                    numManager.setRemainingDays("0");
                }
                updateExpiryDate = true;
            }

            if (cardExpiryDate != null) {
                if (cardExpiryDate.isAfter(now)) {
                    long day = ChronoUnit.DAYS.between(now, cardExpiryDate);
                    if (day >= 0) {
                        numManager.setCardRemainingDays(String.valueOf(day));
                    }
                } else {
                    numManager.setCardRemainingDays("0");
                }
                updateCardExpiryDate = true;
            }

            if (updateExpiryDate || updateCardExpiryDate) {
                int i = numManagerMapper.updateById(numManager);
                num += i;
            }
        }

        stopwatch.stop();
        long elapsedTime = stopwatch.getTime();
        log.warn("定时任务执行结束,耗时:{} ms,更新总数:{}", elapsedTime, num);
    }
}
