package com.yupi.yuaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @program: yu-ai-agent
 * @description:
 * @author: Miao Zheng
 * @date: 2025-11-28 18:00
 **/
@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void doChat() {
        String chatId = UUID.randomUUID().toString();
        // 1
        String message = "你好, 我是leo";
        String answer = loveApp.doChat(message, chatId);
        // 2
        message = "我想让我的另一半 cecilia 一直开心健康";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 3
        message = "我的另一半叫什么来着？ 刚跟你说过，帮我回忆一下";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }


    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        // 1
        String message = "你好, 我是leo,我想让另一半 （cecilia） 更爱我，但我不知道该怎么做";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);

    }

    @Test
    void doChatWithRagLocal() {

        String chatId = UUID.randomUUID().toString();
        // 1
        String message = "你好, 我是leo, 我已经结婚了。我现在的婚恋生活不和谐，我该怎么做？";
        String res = loveApp.doChatWithRagLocal(message, chatId);
        Assertions.assertNotNull(res);
    }

}