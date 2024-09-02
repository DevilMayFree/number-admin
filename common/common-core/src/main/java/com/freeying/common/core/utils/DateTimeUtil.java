package com.freeying.common.core.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * DateTimeUtil
 * <p>LocalDateTime工具</p>
 *
 * @author fx
 */
public final class DateTimeUtil {

    private DateTimeUtil() {
    }

    /**
     * localDateTime转Instant
     *
     * @param localDateTime localDateTime
     * @return Instant
     */
    public static Instant toInstant(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();

        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return zonedDateTime.toInstant();
    }
}
