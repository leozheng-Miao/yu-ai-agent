package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yuaiagent.entity.ChatMessage;
import generator.service.ChatMessageService;
import com.yupi.yuaiagent.mapper.ChatMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author zhengsmacbook
* @description 针对表【chat_message(AI 会话消息表)】的数据库操作Service实现
* @createDate 2025-12-01 15:35:14
*/
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage>
    implements ChatMessageService{

}




