package com.freeying.framework.data.snowflake;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Snowflake key generate algorithm.
 *
 * <pre>
 *     Length of key is 64 bit.
 *     1 bit sign bit.
 *     41 bits timestamp offset from 2016.11.01(ShardingSphere distributed primary key published data) to now.
 *     change start from 2020.11.01
 *     10 bits worker process id.
 *     12 bits auto increment offset in one mills
 * </pre>
 *
 * @author fx
 */
public class SnowflakeKeyGenerateAlgorithm implements KeyGenerateAlgorithm {
    private static final Logger log = LoggerFactory.getLogger(SnowflakeKeyGenerateAlgorithm.class);

    public static final long EPOCH;

    private static final String WORKER_ID_KEY = "worker-id";

    private static final String MAX_VIBRATION_OFFSET_KEY = "max-vibration-offset";

    private static final String MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS_KEY = "max-tolerate-time-difference-milliseconds";

    private static final long SEQUENCE_BITS = 12L;

    private static final long WORKER_ID_BITS = 10L;

    private static final long SEQUENCE_MASK = (1 << SEQUENCE_BITS) - 1L;

    private static final long WORKER_ID_LEFT_SHIFT_BITS = SEQUENCE_BITS;

    private static final long TIMESTAMP_LEFT_SHIFT_BITS = WORKER_ID_LEFT_SHIFT_BITS + WORKER_ID_BITS;

    private static final long WORKER_ID_MAX_VALUE = 1L << WORKER_ID_BITS;

    private static final long WORKER_ID = 0;

    private static final int DEFAULT_VIBRATION_VALUE = 1;

    private static final int MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS = 10;

    private static TimeService timeService = new TimeService();

    private Properties props = new Properties();

    private long workerId;

    private int maxVibrationOffset;

    private int maxTolerateTimeDifferenceMilliseconds;

    private int sequenceOffset = -1;

    private long sequence;

    private long lastMilliseconds;

    static {
        /**
         * 1288834974657L is 2010-11-04 09:42:54
         * 1680411660000L is 2023-04-02 13:01:00
         */
        EPOCH = 1680411660000L;
    }

    public void init() {
        workerId = getWorkerId();
        maxVibrationOffset = getMaxVibrationOffset();
        maxTolerateTimeDifferenceMilliseconds = getMaxTolerateTimeDifferenceMilliseconds();
    }

    private long getWorkerId() {
        long result = Long.parseLong(props.getOrDefault(WORKER_ID_KEY, WORKER_ID).toString());
        Preconditions.checkArgument(result >= 0L && result < WORKER_ID_MAX_VALUE, "Illegal worker id.");
        return result;
    }

    private int getMaxVibrationOffset() {
        int result = Integer.parseInt(props.getOrDefault(MAX_VIBRATION_OFFSET_KEY, DEFAULT_VIBRATION_VALUE).toString());
        Preconditions.checkArgument(result >= 0 && result <= SEQUENCE_MASK, "Illegal max vibration offset.");
        return result;
    }

    private int getMaxTolerateTimeDifferenceMilliseconds() {
        return Integer.parseInt(props.getOrDefault(MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS_KEY, MAX_TOLERATE_TIME_DIFFERENCE_MILLISECONDS).toString());
    }

    @Override
    public synchronized Number generateKey() {
        long currentMilliseconds = timeService.getCurrentMillis();
        if (waitTolerateTimeDifferenceIfNeed(currentMilliseconds)) {
            currentMilliseconds = timeService.getCurrentMillis();
        }
        if (lastMilliseconds == currentMilliseconds) {
            if (0L == (sequence = (sequence + 1) & SEQUENCE_MASK)) {
                currentMilliseconds = waitUntilNextTime(currentMilliseconds);
            }
        } else {
            vibrateSequenceOffset();
            sequence = sequenceOffset;
        }
        lastMilliseconds = currentMilliseconds;
        return ((currentMilliseconds - EPOCH) << TIMESTAMP_LEFT_SHIFT_BITS) | (workerId << WORKER_ID_LEFT_SHIFT_BITS) | sequence;
    }

    private boolean waitTolerateTimeDifferenceIfNeed(final long currentMilliseconds){
        if (lastMilliseconds <= currentMilliseconds) {
            return false;
        }
        long timeDifferenceMilliseconds = lastMilliseconds - currentMilliseconds;
        Preconditions.checkState(timeDifferenceMilliseconds < maxTolerateTimeDifferenceMilliseconds,
                "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", lastMilliseconds, currentMilliseconds);
        try {
            Thread.sleep(timeDifferenceMilliseconds);
        }catch (InterruptedException e){
            log.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        return true;
    }

    private long waitUntilNextTime(final long lastTime) {
        long result = timeService.getCurrentMillis();
        while (result <= lastTime) {
            result = timeService.getCurrentMillis();
        }
        return result;
    }

    private void vibrateSequenceOffset() {
        sequenceOffset = sequenceOffset >= maxVibrationOffset ? 0 : sequenceOffset + 1;
    }

    public static void setTimeService(TimeService timeService) {
        SnowflakeKeyGenerateAlgorithm.timeService = timeService;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }
}