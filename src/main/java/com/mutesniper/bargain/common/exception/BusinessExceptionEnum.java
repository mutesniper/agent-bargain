package com.mutesniper.bargain.common.exception; 

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务异常枚举
 */
@Getter
@AllArgsConstructor
public enum BusinessExceptionEnum {

    /**待扩展 */
    // === 通用 ===
    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(500, "系统内部错误"),
    PARAM_INVALID(400, "参数校验失败"),

    // === 商品域 (40001 - 40099) ===
    ITEM_NOT_FOUND(40001, "商品不存在或已下架"),
    ITEM_NOT_OWNED(40002, "您无权操作该商品"),

    // === 议价域 (40101 - 40199) ===
    NEGOTIATION_NOT_FOUND(40101, "议价会话不存在"),
    NEGOTIATION_FINISHED(40102, "该议价已结束，无法继续出价"),
    MAX_ROUND_REACHED(40103, "已达到最大议价轮次，谈判破裂"),
    PRICE_OUT_OF_BOUND(40104, "Agent 出价超出授权边界，已被硬拦截"), // 硬边界校验
    SELF_BARGAIN_FORBIDDEN(40105, "不能和自己进行议价"),

    // === 订单域 (40201 - 40299) -> HITL 双确认用 ===
    ORDER_ALREADY_CONFIRMED(40201, "您已确认过该订单，请勿重复操作"),
    ORDER_NOT_FOUND(40202, "订单不存在");

    private final int code;
    private final String msg;
}