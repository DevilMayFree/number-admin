package com.freeying.common.webmvc.request.mapping;

import com.freeying.common.webmvc.request.annotation.ApiVersion;
import com.freeying.common.webmvc.request.condition.ApiVersionCondition;
import io.micrometer.common.lang.NonNull;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * ApiVersionHandlerMapping
 *
 * @author fx
 */
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected boolean isHandler(@NonNull Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class);
    }

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomTypeCondition(@NonNull Class<?> handlerType) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
        return createCondition(apiVersion);
    }

    @Override
    protected RequestCondition<ApiVersionCondition> getCustomMethodCondition(@NonNull Method method) {
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        return createCondition(apiVersion);
    }

    private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion) {
        return apiVersion == null ? null : new ApiVersionCondition(apiVersion.value(), apiVersion.headerKey());
    }
}
