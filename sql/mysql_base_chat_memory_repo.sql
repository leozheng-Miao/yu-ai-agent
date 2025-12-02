-- 创建库
create database if not exists yu_ai_agent;

-- 切换库
use yu_ai_agent;

CREATE TABLE IF NOT EXISTS `chat_message`
(
    `id`              BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键ID',

    `conversation_id` VARCHAR(64) NOT NULL COMMENT '会话ID',
    `msg_index`       INT         NOT NULL COMMENT '消息在会话中的顺序（从0开始，可用于排序）',

    `role`            VARCHAR(32) NOT NULL COMMENT '消息角色：system/user/assistant/tool/function 等',
    `content`         LONGTEXT    NOT NULL COMMENT '消息内容（文本或JSON字符串）',

    `metadata`        JSON                 DEFAULT NULL COMMENT '额外元数据，例如工具调用参数、function call、模型信息等',

    `create_time`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    PRIMARY KEY (`id`),
    KEY `idx_conv_id` (`conversation_id`),
    KEY `idx_conv_order` (`conversation_id`, `msg_index`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='AI 会话消息表';


