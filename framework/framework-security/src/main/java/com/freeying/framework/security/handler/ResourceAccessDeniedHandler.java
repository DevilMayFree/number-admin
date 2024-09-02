package com.freeying.framework.security.handler;

import com.freeying.common.core.utils.WebUtil;
import com.freeying.common.core.web.Result;
import com.freeying.common.core.web.ResultCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * ResourceAccessDeniedHandler
 * <p>无权限访问</p>
 *
 * @author fx
 */
public class ResourceAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        Result<String> deniedError = Result.error(
                ResultCode.UN_AUTHORIZED,
                accessDeniedException.getLocalizedMessage(),
                ResultCode.UN_AUTHORIZED.getMessage()
        );

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        WebUtil.renderJson(response, deniedError);
    }
}
