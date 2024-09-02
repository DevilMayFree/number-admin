package com.freeying.common.core.jackson;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * JacksonConfiguration
 *
 * @author fx
 */
@AutoConfiguration
@ConditionalOnClass(ObjectMapper.class)
public class JacksonConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            // 时区设置
            builder.locale(Locale.CHINA);
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            // 解决Jackson导致Long型数据精度丢失问题
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            // 日期格式解析
            builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
            // 失败处理
            builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            builder.modules(new JavaTimeModule(), new JacksonJavaTimeModule());
        };
    }

}
