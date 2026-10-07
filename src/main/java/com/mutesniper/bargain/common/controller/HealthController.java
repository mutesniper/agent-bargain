package com.mutesniper.bargain.common.controller;

import com.mutesniper.bargain.common.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用
 */
@RestController
public class HealthController {
    @RequestMapping("/health")
    public Result<String> health() {
        return Result.success("Agent Bargain Platform is running!");

    }
}
