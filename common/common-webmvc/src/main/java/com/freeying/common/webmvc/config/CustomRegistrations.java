package com.freeying.common.webmvc.config;

import com.freeying.common.webmvc.request.mapping.ApiVersionHandlerMapping;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * CustomRegistrations
 *
 * @author fx
 */
@AutoConfiguration
public class CustomRegistrations implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionHandlerMapping();
    }
}
