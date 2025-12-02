package com.yupi.yuaiagent.chatmemory;

import com.yupi.yuaiagent.chatmemory.repository.MysqlBasedChatMemoryRepository;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

/**
 * @program: yu-ai-agent
 * @description:
 * @author: Miao Zheng
 * @date: 2025-12-01 16:20
 **/
public class MysqlBasedChatMemory implements ChatMemory {



    @Override
    public void add(String conversationId, List<Message> messages) {

    }

    @Override
    public List<Message> get(String conversationId) {
        return List.of();
    }

    @Override
    public void clear(String conversationId) {

    }
}
