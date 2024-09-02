package com.freeying.common.webmvc.utils;

import com.freeying.common.core.utils.SpringContextHolder;
import com.freeying.common.core.utils.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static java.util.Locale.SIMPLIFIED_CHINESE;
import static java.util.Locale.US;

/**
 * I18nUtil
 *
 * @author fx
 */
public final class I18nUtil {
    private static final MessageSource MESSAGE_SOURCE = SpringContextHolder.getBean(MessageSource.class);

    private I18nUtil() {
    }

    /**
     * 获取当前语言环境
     * <p>
     * zh-CN,en-US
     * </p>
     *
     * @return language
     */
    public static String getLanguage() {
        return LocaleContextHolder.getLocale().toLanguageTag();
    }

    /**
     * 信息国际化转换
     *
     * @param msg msg
     * @return i18n msg
     */
    public static String getTranslation(String msg) {
        if (StringUtils.isBlank(msg)) {
            return StringPool.EMPTY;
        }
        try {
            return MESSAGE_SOURCE.getMessage(msg, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msg;
        }
    }

    /**
     * 信息国际化转换
     *
     * @param msg msg
     * @return i18n msg
     */
    public static String getTranslation(String msg, String defaultMsg) {
        if (StringUtils.isBlank(msg)) {
            return StringPool.EMPTY;
        }
        try {
            return MESSAGE_SOURCE.getMessage(msg, null, defaultMsg, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return msg;
        }
    }

    /**
     * 字典国际化转换
     *
     * @param zh 中文内容
     * @param en 英文内容
     * @return 翻译文本
     */
    public static String translation(String zh, String en) {
        if (StringUtils.isBlank(zh) || StringUtils.isBlank(en)) {
            return StringPool.EMPTY;
        }
        Locale locale = LocaleContextHolder.getLocale();
        if (SIMPLIFIED_CHINESE.equals(locale)) {
            return zh;
        } else if (US.equals(locale)) {
            return en;
        }
        return StringPool.EMPTY;
    }

}
