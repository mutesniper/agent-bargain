package com.mutesniper.bargain.common.exception;

import lombok.Getter;

/**
 * 业务异常类
 */
public class BusinessException extends RuntimeException {

    @Getter
    private final int code;

    public BusinessException(BusinessExceptionEnum businessExceptionEnum) {
        super(businessExceptionEnum.getMsg());
        this.code = businessExceptionEnum.getCode();
    }

}
