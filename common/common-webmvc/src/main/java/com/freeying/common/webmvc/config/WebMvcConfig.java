package com.freeying.common.webmvc.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

/**
 * WebMvcConfig
 *
 * @author fx
 */
@AutoConfiguration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 自定义messageSource
     * 接收子模块的messages
     * Header中添加Accept-Language = zh ｜ en 切换
     *
     * @return ReloadableResourceBundleMessageSource
     */
    @ConditionalOnMissingBean
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ValidationResourceBundleMessageSource messageBundle = new ValidationResourceBundleMessageSource();
        messageBundle.setBasename("classpath*:i18n/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }

    /**
     * 覆盖getValidator
     *
     * @return Validator
     */
    @Bean
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    /**
     * Jackson2ObjectMapperBuilderCustomizer
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                .deserializerByType(String.class, new StdScalarDeserializer<String>(String.class) {
                    @Override
                    public String deserialize(JsonParser jsonParser, DeserializationContext ctx)
                            throws IOException {
                        return jsonParser.getValueAsString().strip();
                    }
                });
    }

    /**
     * 跨域设置
     *
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                //配置允许跨域的路径
                .addMapping("/**")
                //配置允许访问的跨域资源的请求域名
                .allowedOrigins("*")
                //配置允许访问该跨域资源服务器的请求方法，如：POST、GET、PUT、DELETE等
                .allowedMethods("*")
                //配置允许请求header的访问，如 ：X-TOKEN
                .allowedHeaders("*");
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

}
