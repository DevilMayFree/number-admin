package com.freeying.framework.security.handler;

import com.freeying.common.core.web.Result;
import com.freeying.common.core.web.ResultCode;
import com.freeying.common.core.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * ResourceAuthExceptionEntryPoint
 * <p>客户端异常处理</p>
 *
 * @author fx
 */
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        int status = response.getStatus();

        String errDescription = authException.getLocalizedMessage();

        if (HttpServletResponse.SC_NOT_FOUND == status) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            WebUtil.renderJson(response, Result.error(
                    ResultCode.RESOURCE_NOT_FOUND,
                    errDescription,
                    ResultCode.RESOURCE_NOT_FOUND.getMessage()
            ));
        } else {
            // SC_UNAUTHORIZED -> SC_OK
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            WebUtil.renderJson(response, Result.error(
                    ResultCode.NO_AUTHORIZED,
                    errDescription,
                    ResultCode.NO_AUTHORIZED.getMessage()
            ));
        }
    }
}
