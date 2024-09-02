package com.freeying.common.core.utils;

import com.freeying.common.core.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * WebUtil
 * <p>Spring WebUtils 扩展</p>
 *
 * @author fx
 */
public final class WebUtil extends WebUtils {
    private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

    private WebUtil() {
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取 HttpServletRequest
     *
     * @return {HttpServletRequest}
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new ServiceException("getRequest HttpServletRequest is empty");
        }
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取 HttpServletResponse
     *
     * @return {HttpServletResponse}
     */
    public HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = getServletRequestAttributes();
        if (servletRequestAttributes == null) {
            throw new ServiceException("getResponse HttpServletRequest is empty");
        }
        return servletRequestAttributes.getResponse();
    }

    /**
     * 客户端返回JSON字符串
     *
     * @param response HttpServletResponse
     * @param object   返回的对象
     */
    public static void renderJson(HttpServletResponse response, Object object) {
        renderJson(response, JsonUtil.toJson(object), StringPool.APPLICATION_JSON_UTF8_VALUE);
    }

    /**
     * 客户端返回字符串
     *
     * @param response HttpServletResponse
     * @param value    value
     * @param type     type
     */
    public static void renderJson(HttpServletResponse response, String value, String type) {
        response.setHeader(HttpHeaders.CONTENT_TYPE, type);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, StringPool.ACCESS_CONTROL_ALLOW_ORIGIN_ANY);
        response.setHeader(HttpHeaders.CACHE_CONTROL, StringPool.CACHE_CONTROL_NO_CACHE);
        try {
            PrintWriter writer = response.getWriter();
            writer.print(value);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
