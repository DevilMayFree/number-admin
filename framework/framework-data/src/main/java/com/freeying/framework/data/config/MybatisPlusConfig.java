package com.freeying.framework.data.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.IdentifierGeneratorAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.freeying.framework.data.core.CustomSqlInjector;
import com.freeying.framework.data.generator.CustomIdGenerator;
import com.freeying.framework.data.handler.CustomMetaObjectHandler;
import com.freeying.framework.data.handler.LocalDateTimeTypeHandler;
import com.freeying.framework.data.interceptor.OrderByInnerInterceptor;
import org.apache.ibatis.type.LocalDateTypeHandler;
import org.apache.ibatis.type.LocalTimeTypeHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * MybatisPlusConfig
 *
 * @author fx
 */
@AutoConfiguration
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@AutoConfigureBefore({MybatisPlusAutoConfiguration.class, IdentifierGeneratorAutoConfiguration.class})
public class MybatisPlusConfig {

    public MybatisPlusConfig(MybatisPlusProperties mybatisPlusProperties) {
        MybatisConfiguration configuration = mybatisPlusProperties.getConfiguration();
        GlobalConfig globalConfig = mybatisPlusProperties.getGlobalConfig();
        GlobalConfigUtils.setGlobalConfig(configuration, globalConfig);
        configuration.getTypeHandlerRegistry().register(LocalDateTime.class, new LocalDateTimeTypeHandler());
        configuration.getTypeHandlerRegistry().register(LocalDate.class, new LocalDateTypeHandler());
        configuration.getTypeHandlerRegistry().register(LocalTime.class, new LocalTimeTypeHandler());
        GlobalConfigUtils.setGlobalConfig(configuration, globalConfig);
    }

    /**
     * 分页插件
     * 多租户插件
     * 性能分析插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OrderByInnerInterceptor());
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    public IdentifierGenerator customIdGenerator() {
        return new CustomIdGenerator();
    }

    /**
     * 字段自动填充
     *
     * @return MetaObjectHandler
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new CustomMetaObjectHandler();
    }

    /**
     * 扩展BaseMapper
     *
     * @return CustomSqlInjector
     */
    @Bean
    public CustomSqlInjector customSqlInjector() {
        return new CustomSqlInjector();
    }
}
