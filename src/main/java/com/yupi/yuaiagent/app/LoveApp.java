package com.yupi.yuaiagent.app;

import com.yupi.yuaiagent.advisor.MyLoggerAdvisor;
import com.yupi.yuaiagent.advisor.MyReReadingAdvisor;
import com.yupi.yuaiagent.chatmemory.FileBasedChatMemory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: yu-ai-agent
 * @description:
 * @author: Miao Zheng
 * @date: 2025-11-28 14:06
 **/
@Component
@Slf4j
public class LoveApp {

    private final ChatClient chatClient;
    private static final String SYSTEM_PROMPT = "扮演深耕恋爱心理领域的专家。开场向用户表明身份，告知用户可倾诉恋爱难题。" +
            "围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；" +
            "恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。" +
            "引导用户详述事情经过、对方反应及自身想法，以便给出专属解决方案。";


    /**
     * 构造函数，传入ChatModel对象
     *
     * @param dashscopeChatModel
     */
    public LoveApp(ChatModel dashscopeChatModel) {

        // 基于文件的对话记忆
//        String firDir = System.getProperty("user.dir") + "/tmp/chat-memory";
//        ChatMemory memory = new FileBasedChatMemory(firDir);

        // 基于内存的对话记忆
        MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(20)
                .build();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(memory).build()
//                        new MyLoggerAdvisor()
//                        new MyReReadingAdvisor()
                )
                .build();

    }

    /**
     * ai 基础对话
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChat(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(
                        advisor -> advisor.param(ChatMemory.CONVERSATION_ID, chatId)
                )
                .call()
                .chatResponse();

        String res = chatResponse.getResult().getOutput().getText();
        log.info("chatId: {}, message: {}", chatId, res);
        return res;

    }

    record LoveReport(String title, List<String> suggestions) {

    }

    /**
     * 实战 结构化输出
     * 该方法用于处理用户聊天请求并生成恋爱报告

 *
     * @param message 用户发送的消息内容
     * @param chatId 聊天会话的唯一标识符
     * @return LoveReport 包含恋爱报告和建议列表的结果对象
     */
    public LoveReport doChatWithReport(String message, String chatId) {
    // 使用chatClient创建聊天请求，配置系统提示、用户消息和聊天记忆
        LoveReport loveReport = chatClient
                .prompt()  // 开始创建提示
                .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")  // 设置系统提示
                .user(message)  // 设置用户输入的消息
                .advisors(  // 配置顾问参数
                        advisor -> advisor.param(ChatMemory.CONVERSATION_ID, chatId)  // 设置会话ID
                )
                .call()  // 执行聊天请求
                .entity(LoveReport.class);  // 将响应结果转换为LoveReport对象

    // 记录聊天日志，包含chatId和生成的恋爱报告
        log.info("chatId: {}, loveReport: {}", chatId, loveReport);
        return loveReport;  // 返回生成的恋爱报告

    }

    @Resource
    private VectorStore loveAppVectorStore;

    public String doChatWithRagLocal(String message, String chatId) {

        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(  // 配置顾问参数
                        advisor -> advisor.param(ChatMemory.CONVERSATION_ID, chatId)  // 设置会话ID
                )
                .advisors(new MyLoggerAdvisor())
                .advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
                .call()
                .chatResponse();
        String res = chatResponse.getResult().getOutput().getText();
        log.info("chatId: {}, message: {}", chatId, res);
        return res;

    }
}
