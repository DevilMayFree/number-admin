package com.freeying.common.webmvc.request.condition;

import io.micrometer.common.lang.NonNull;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import jakarta.servlet.http.HttpServletRequest;

/**
 * APIVersionCondition
 *
 * @author fx
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    private final String apiVersion;

    private final String headerKey;

    public ApiVersionCondition(String apiVersion, String headerKey) {
        this.apiVersion = apiVersion;
        this.headerKey = headerKey;
    }

    @Override
    @NonNull
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.getApiVersion(), other.getHeaderKey());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        String version = request.getHeader(headerKey);
        return apiVersion.equals(version) ? this : null;
    }

    @Override
    public int compareTo(@NonNull ApiVersionCondition other, @NonNull HttpServletRequest request) {
        return 0;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getHeaderKey() {
        return headerKey;
    }
}
