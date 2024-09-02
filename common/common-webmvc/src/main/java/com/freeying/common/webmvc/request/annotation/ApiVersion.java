package com.freeying.common.webmvc.request.annotation;

import com.freeying.common.core.constant.ApiVersionConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ApiVersion版本控制注解
 * 作用于controller类上或者类中方法上
 *
 * @author fx
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiVersion {

    String value();

    String headerKey() default ApiVersionConstants.VERSION;
}
