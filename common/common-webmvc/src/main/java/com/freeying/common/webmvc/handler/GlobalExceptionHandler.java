package com.freeying.common.webmvc.handler;

import com.freeying.common.core.constant.HttpConstants;
import com.freeying.common.core.exception.BadRequestException;
import com.freeying.common.core.exception.OrderByException;
import com.freeying.common.core.exception.ServiceException;
import com.freeying.common.core.utils.StringPool;
import com.freeying.common.core.web.Result;
import com.freeying.common.core.web.ResultCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author fx
 */
@Order(Integer.MIN_VALUE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 异常
     *
     * @param e e
     * @return Result
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result<Object> exceptionHandler(final Exception e) {
        if (log.isErrorEnabled()) {
            log.error(e.getMessage(), e);
        }
        String simpleName = e.getClass().getSimpleName();
        String message = e.getLocalizedMessage();
        return Result.error(
                ResultCode.SYSTEM_RESOURCE_EXCEPTION,
                simpleName + ":" + message,
                ResultCode.SYSTEM_RESOURCE_EXCEPTION.getMessage());
    }

    /**
     * 业务异常
     *
     * @param e serviceException
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    public Result<Object> serviceExceptionHandler(final ServiceException e) {
        if (log.isWarnEnabled()) {
            log.warn("Service error: {}", e.getMessage());
        }
        String message = e.getLocalizedMessage();
        return Result.error(
                ResultCode.ERROR,
                message,
                ResultCode.ERROR.getMessage());
    }

    /**
     * 请求异常
     *
     * @param e serviceException
     * @return Result
     */
    @ExceptionHandler(BadRequestException.class)
    public Result<Object> badRequestExceptionHandler(final BadRequestException e) {
        if (log.isWarnEnabled()) {
            log.warn("badRequest error: {}", e.getMessage());
        }
        String message = e.getLocalizedMessage();
        return Result.error(
                ResultCode.REQUEST_ERROR,
                ResultCode.REQUEST_ERROR.getMessage(),
                message
        );
    }

    /**
     * 认证异常
     *
     * @param e AccessDeniedException
     * @return Result
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<Object> accessDeniedExceptionHandler(final AccessDeniedException e) {
        String message = e.getLocalizedMessage();
        return Result.error(
                ResultCode.UN_AUTHORIZED,
                message,
                ResultCode.UN_AUTHORIZED.getMessage()
        );
    }

    /**
     * 接口httpMessage数据转换异常
     *
     * @param e HttpMessageNotReadableException
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Object> httpMessageNotReadableExceptionHandler(final HttpMessageNotReadableException e) {
        if (log.isWarnEnabled()) {
            log.warn("httpMessageNotReadableException error: {}", e.getMessage());
        }
        String message = e.getLocalizedMessage();

        // header中添加`cause`则返回完整异常信息
        HttpInputMessage httpInputMessage = e.getHttpInputMessage();
        List<String> list = httpInputMessage.getHeaders().get(HttpConstants.HTTP_HEADER_CAUSE);
        if (CollectionUtils.isEmpty(list) && (StringUtils.isNotBlank(message))) {
            String errMsg = message.split(StringPool.SPLIT_EMPTY)[0];
            if (StringUtils.isNotBlank(errMsg)) {
                message = errMsg.replace(StringPool.JAVA_LANG, StringPool.EMPTY);
            }

            String showMsg = message.split(StringPool.SPLIT_PROFANITY)[0];
            if (StringUtils.isNotBlank(showMsg)) {
                message = showMsg;
            }
        }

        return Result.error(
                ResultCode.ERROR,
                message,
                ResultCode.ERROR.getMessage());
    }


    /**
     * 请求参数校验失败
     *
     * @param e e
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Result<Map<String, String>> bindExceptionHandler(final BindException e) {
        return bindExceptionSupport(e);
    }

    /**
     * 请求体校验失败
     *
     * @param e e
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Map<String, String>> methodArgumentNotValidExceptionHandler(final MethodArgumentNotValidException e) {
        return bindExceptionSupport(e);
    }

    /**
     * 服务体校验失败
     *
     * @param e e
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> illegalArgumentExceptionHandler(final IllegalArgumentException e) {
        return illegalArgumentExceptionSupport(e);
    }

    /**
     * 类上的@Validated 普通参数校验失败
     *
     * @param e e
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Map<String, String>> handler(final ConstraintViolationException e) {
        Map<String, String> errorMap = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        constraintViolation -> Optional.ofNullable(constraintViolation)
                                .map(constraint -> {
                                    String propertyPath = constraint.getPropertyPath().toString();
                                    return propertyPath.split("\\.")[1];
                                }).orElse(StringPool.EMPTY),
                        constraintViolation -> Optional.ofNullable(constraintViolation)
                                .map(ConstraintViolation::getMessage).orElse(StringPool.EMPTY),
                        (key1, key2) -> key2));
        return Result.error(ResultCode.VALIDATE_ERROR, errorMap);
    }

    /**
     * 请求参数校验失败
     *
     * @param e e
     * @return Result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderByException.class)
    public Result<Map<String, String>> orderByExceptionHandler(final OrderByException e) {
        return Result.error(ResultCode.VALIDATE_ERROR, e.getMessage());
    }

    /**
     * 参数校验失败返回体自定义
     *
     * @param e e
     * @return Result
     */
    private Result<Map<String, String>> bindExceptionSupport(BindException e) {
        Map<String, String> errorMap = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> Optional.ofNullable(fieldError)
                                .map(FieldError::getField).orElse(StringPool.EMPTY),
                        fieldError -> Optional.ofNullable(fieldError)
                                .map(FieldError::getDefaultMessage).orElse(StringPool.EMPTY),
                        (key1, key2) -> key2));
        return Result.error(ResultCode.VALIDATE_ERROR, errorMap);
    }

    /**
     * 业务异常
     *
     * @param e IllegalArgumentException
     * @return Result
     */
    private Result<String> illegalArgumentExceptionSupport(IllegalArgumentException e) {
        return Result.error(ResultCode.VALIDATE_ERROR, e.getMessage(), ResultCode.VALIDATE_ERROR.getMessage());
    }

}
