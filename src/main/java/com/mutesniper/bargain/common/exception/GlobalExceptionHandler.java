package com.mutesniper.bargain.common.exception;

import com.mutesniper.bargain.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截业务异常：传递错误码和提示给前端
     * @param businessException
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException businessException) {
        log.warn("业务异常：code={},msg={}", businessException.getCode(), businessException.getMessage());
        return Result.fail(businessException.getCode(), businessException.getMessage());

    }

    /**
     * 兜底拦截系统异常
     * @param throwable
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public Result<?> handleThrowable(Throwable throwable) {
        log.error("系统内部错误", throwable);
        return Result.fail(Result.CODE_SERVER_ERROR, "系统内部错误，请稍后重试");
    }
}
