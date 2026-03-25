package com.volcengine.vikingdb.runtime.knowledge.util;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.volcengine.vikingdb.runtime.core.ApiClient;
import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.core.auth.Auth;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithApiKey;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.knowledge.model.CollectionMeta;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ChatCompletionRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ChatMessage;
import com.volcengine.vikingdb.runtime.knowledge.model.response.ChatCompletionResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.SearchKnowledgeResponse;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeService;

import java.util.Arrays;
import java.util.List;

public final class ExampleUtil {
    private static final ObjectWriter PRETTY_JSON = ApiClient.objectMapper.writerWithDefaultPrettyPrinter();

    private ExampleUtil() {
    }

    public static KnowledgeService newKnowledgeService(Auth auth) {
        Scheme scheme = Scheme
                .fromString(envOrDefault("VIKING_KNOWLEDGE_SCHEME", envOrDefault("VIKING_SCHEME", "https")));
        String host = envOrDefault("VIKING_KNOWLEDGE_HOST",
                envOrDefault("VIKING_HOST", "api-knowledgebase.mlp.cn-beijing.volces.com"));
        String region = envOrDefault("VIKING_KNOWLEDGE_REGION", envOrDefault("VIKING_REGION", "cn-beijing"));
        return new KnowledgeService(scheme, host, region, auth);
    }

    public static Auth preferAuth() {
        String ak = envOrDefault("VOLC_AK", "");
        String sk = envOrDefault("VOLC_SK", "");
        if (!ak.isEmpty() && !sk.isEmpty()) {
            return new AuthWithAkSk(ak, sk);
        }
        String fallbackApiKey = envOrDefault("VIKING_API_KEY", "");
        if (!fallbackApiKey.isEmpty()) {
            return new AuthWithApiKey(fallbackApiKey);
        }
        return null;
    }

    public static Auth apiKeyAuthFromEnv(String envName) {
        String apiKey = envOrDefault(envName, "");
        if (apiKey.isEmpty()) {
            return null;
        }
        return new AuthWithApiKey(apiKey);
    }

    public static CollectionMeta defaultCollectionMeta() {
        String collectionName = envOrDefault("VIKING_COLLECTION_NAME", "financial_reports");
        String projectName = envOrDefault("VIKING_PROJECT", "default");
        String resourceId = envOrDefault("VIKING_COLLECTION_RID", "");
        return CollectionMeta.builder()
                .collectionName(collectionName)
                .projectName(projectName)
                .resourceId(resourceId)
                .build();
    }

    public static String envOrDefault(String name, String defaultValue) {
        String v = System.getenv(name);
        if (v == null) {
            return defaultValue;
        }
        v = v.trim();
        return v.isEmpty() ? defaultValue : v;
    }

    public static void printJson(String name, Object obj) throws Exception {
        if (obj == null) {
            System.out.println(name + ": null");
            return;
        }
        System.out.println(name + ": " + PRETTY_JSON.writeValueAsString(obj));
    }

    public static ChatCompletionResponse chatCompletion(KnowledgeService service, SearchKnowledgeResponse sk,
            String query) throws Exception {
        String contextText = buildContext(sk);
        String systemPrompt = "你是一位专业的财报分析师，你需要根据「参考资料」来回答接下来的「用户问题」，这些信息在 <context></context> XML 标签之内。回答必须在参考资料范围内，尽可能简洁，无法回答时请礼貌说明并引导提供更多信息。\n\n<context>\n"
                + contextText
                + "\n</context>";

        List<ChatMessage> messages = Arrays.asList(
                ChatMessage.builder().role("system").content(systemPrompt).build(),
                ChatMessage.builder().role("user").content(query).build());

        ChatCompletionRequest chatReq = ChatCompletionRequest.builder()
                .model(envOrDefault("VIKING_CHAT_MODEL", "Doubao-1-5-pro-32k"))
                .messages(messages)
                .maxTokens(4096)
                .temperature(0.1)
                .returnTokenUsage(true)
                .apiKey(envOrDefault("VIKING_CHAT_API_KEY", ""))
                .stream(false)
                .build();
        return service.chatCompletion(chatReq,
                new RequestAddition().setRequestId("knowledge_chat_completion_java_example"));
    }

    public static String buildContext(SearchKnowledgeResponse sk) {
        if (sk == null || sk.getData() == null || sk.getData().getResultList() == null
                || sk.getData().getResultList().isEmpty()) {
            return "（检索结果为空或不可用）";
        }
        StringBuilder sb = new StringBuilder();
        int limit = Math.min(5, sk.getData().getResultList().size());
        for (int i = 0; i < limit; i++) {
            if (i > 0) {
                sb.append("\n\n");
            }
            String title = sk.getData().getResultList().get(i).getChunkTitle();
            String content = sk.getData().getResultList().get(i).getContent();
            sb.append("【").append(title == null ? "" : title).append("】\n").append(content == null ? "" : content);
        }
        return sb.toString();
    }
}
