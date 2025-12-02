package com.yupi.yuaiagent.demo.invoke;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpAiInvoke {

    // 替换为您的 DashScope API Key
    private static final String DASHSCOPE_API_KEY = TestApiKey.API_KEY;
    private static final String API_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

    public static void main(String[] args) {
        // 1. 构造请求体 JSON 数据
        Map<String, Object> requestBody = buildRequestBody();

        // 2. 发送 POST 请求（Hutool 自动处理 JSON 序列化和请求头）
        try (HttpResponse response = HttpRequest.post(API_URL)
                // 设置 Bearer 认证头
                .header("Authorization", "Bearer " + DASHSCOPE_API_KEY)
                // 设置 Content-Type 为 JSON
                .contentType("application/json")
                // 将 Map 转为 JSON 字符串（Hutool JSONUtil 自动格式化）
                .body(JSONUtil.toJsonStr(requestBody))
                // 执行请求（超时时间可按需设置，这里设为 30 秒）
                .timeout(30000)
                .execute()) {

            // 3. 处理响应结果
            if (response.isOk()) { // 响应码 200-299
                String responseBody = response.body();
                // 解析 JSON 响应（Hutool JSONUtil 支持格式化输出）
                JSONObject jsonResponse = JSONUtil.parseObj(responseBody);
                System.out.println("响应结果：" + JSONUtil.toJsonPrettyStr(jsonResponse));
            } else {
                System.err.println("请求失败，响应码：" + response.getStatus() + "，错误信息：" + response.body());
            }

        } catch (Exception e) {
            System.err.println("请求异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 构造请求体（对应 curl 中的 --data 部分）
     */
    private static Map<String, Object> buildRequestBody() {
        Map<String, Object> requestBody = new HashMap<>();

        // 设置模型名称
        requestBody.put("model", "qwen-plus");

        // 构造 input -> messages 数组
        List<Map<String, String>> messages = new ArrayList<>();

        // system 消息
        Map<String, String> systemMsg = new HashMap<>();
        systemMsg.put("role", "system");
        systemMsg.put("content", "You are a helpful assistant.");
        messages.add(systemMsg);

        // user 消息
        Map<String, String> userMsg = new HashMap<>();
        userMsg.put("role", "user");
        userMsg.put("content", "你好，我是程序员leo，正在学习java spring ai的开发，目前准备招聘");
        messages.add(userMsg);

        // 封装 input 对象
        Map<String, Object> input = new HashMap<>();
        input.put("messages", messages);
        requestBody.put("input", input);

        // 构造 parameters 对象
        Map<String, String> parameters = new HashMap<>();
        parameters.put("result_format", "message");
        requestBody.put("parameters", parameters);

        return requestBody;
    }
}