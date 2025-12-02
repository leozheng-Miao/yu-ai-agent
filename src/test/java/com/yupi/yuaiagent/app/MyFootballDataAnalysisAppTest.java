package com.yupi.yuaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @program: yu-ai-agent
 * @description:
 * @author: Miao Zheng
 * @date: 2025-12-02 11:30
 **/
@SpringBootTest
class MyFootballDataAnalysisAppTest {

    @Resource
    private MyFootballDataAnalysisApp myFootballDataAnalysisApp;

    @Test
    void doChat() {

        String chatId = UUID.randomUUID().toString();
        // 1
        String message = "你好, 我是leo";
        String answer = myFootballDataAnalysisApp.doChat(message, chatId);
        // 2
        message = "我想知道梅西的今天的赛场表现数据";
        answer = myFootballDataAnalysisApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 3
        message = "我的名字叫什么来着？还有我刚才想要的是谁的数据？ 刚跟你说过，帮我回忆一下";
        answer = myFootballDataAnalysisApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {

        String chatId = UUID.randomUUID().toString();
        // 1
        String message = "你好, 我是leo，我想知道梅西最近一场比赛的赛场表现数据";
        MyFootballDataAnalysisApp.FootBallDataReport footBallDataReport = myFootballDataAnalysisApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(footBallDataReport);



    }
}