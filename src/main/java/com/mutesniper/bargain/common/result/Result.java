package com.mutesniper.bargain.common.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一接口返回封装
 *
 * @param <T> 业务数据类型（泛型，保证类型安全）
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    // 业务状态码：成功
    public static final int CODE_SUCCESS = 200;
    // 业务状态码：服务器内部错误（兜底）
    public static final int CODE_SERVER_ERROR = 500;

    // 业务状态码（200 成功 / 4xxxx 业务失败 / 500 系统错误）
    private int code;
    // 提示信息
    private String msg;
    // 业务数据（失败时为 null）
    private T data;

    /**
     * 私有构造器：强制调用方走静态工厂方法，
     * 避免外部 new 出字段不全的对象
     */
    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /** 成功，无数据 */
    public static <T> Result<T> success() {
        return new Result<>(CODE_SUCCESS, "success", null);
    }

    /** 成功，带数据（最常用） */
    public static <T> Result<T> success(T data) {
        return new Result<>(CODE_SUCCESS, "success", data);
    }

    /** 成功，自定义提示 + 数据 */
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(CODE_SUCCESS, msg, data);
    }

    /** 失败，默认 500（仅用于系统级错误） */
    public static <T> Result<T> fail(String msg) {
        return new Result<>(CODE_SERVER_ERROR, msg, null);
    }

    /** 失败，携带业务错误码（GlobalExceptionHandler 透传 BusinessException 用） */
    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }
}