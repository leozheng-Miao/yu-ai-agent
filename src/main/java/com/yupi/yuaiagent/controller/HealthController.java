package com.yupi.yuaiagent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: yu-ai-agent
 * @description:
 * @author: Miao Zheng
 * @date: 2025-11-27 15:23
 **/
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/check")
    public String healthCheck() {
        return "OK";
    }
}
