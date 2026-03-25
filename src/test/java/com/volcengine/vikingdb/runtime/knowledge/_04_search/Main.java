package com.volcengine.vikingdb.runtime.knowledge._04_search;

import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.core.auth.Auth;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ChatMessage;
import com.volcengine.vikingdb.runtime.knowledge.model.request.RerankDataItem;
import com.volcengine.vikingdb.runtime.knowledge.model.request.RerankRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.SearchCollectionRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.SearchKnowledgeRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ServiceChatRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.response.ChatCompletionResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.RerankResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.SearchKnowledgeResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.SearchResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.ServiceChatResponse;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeCollectionClient;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeService;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeStream;
import com.volcengine.vikingdb.runtime.knowledge.util.ExampleUtil;

import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws Exception {
        KnowledgeService service = ExampleUtil.newKnowledgeService(ExampleUtil.preferAuth());
        KnowledgeCollectionClient kc = service.collection(ExampleUtil.defaultCollectionMeta());

        SearchCollectionRequest scReq = SearchCollectionRequest.builder()
                .query("2025 Q1 revenue growth")
                .limit(10)
                .denseWeight(0.5)
                .rerankSwitch(false)
                .retrieveCount(25)
                .rerankModel(ExampleUtil.envOrDefault("VIKING_RERANK_MODEL", "Doubao-pro-4k-rerank"))
                .rerankOnlyChunk(false)
                .getAttachmentLink(false)
                .build();
        SearchResponse scResp = kc.searchCollection(scReq, new RequestAddition());
        ExampleUtil.printJson("search_collection", scResp);

        SearchKnowledgeRequest skReq = SearchKnowledgeRequest.builder()
                .query("2025 Q1 revenue growth")
                .limit(10)
                .denseWeight(0.5)
                .build();
        SearchKnowledgeResponse skResp = kc.searchKnowledge(skReq, new RequestAddition());
        ExampleUtil.printJson("search_knowledge", skResp);

        ChatCompletionResponse chatResp = ExampleUtil.chatCompletion(service, skResp, "总结下 2025 Q1 财报数据");
        ExampleUtil.printJson("chat_completion", chatResp);

        Auth serviceApiKeyAuth = ExampleUtil.apiKeyAuthFromEnv("VIKING_SERVICE_API_KEY");
        if (serviceApiKeyAuth != null) {
            KnowledgeService serviceWithApiKey = ExampleUtil.newKnowledgeService(serviceApiKeyAuth);
            ServiceChatRequest req = ServiceChatRequest.builder()
                    .serviceResourceId(ExampleUtil.envOrDefault("VIKING_SERVICE_RID", ""))
                    .messages(Collections
                            .singletonList(ChatMessage.builder().role("user").content("列举 2025 Q1 财报里的三项亮点").build()))
                    .stream(false)
                    .build();
            ServiceChatResponse res = serviceWithApiKey.serviceChat(req, new RequestAddition());
            ExampleUtil.printJson("service_chat", res);

            ServiceChatRequest streamReq = req.toBuilder().stream(true).build();
            try (KnowledgeStream<ServiceChatResponse> stream = serviceWithApiKey.serviceChatStream(streamReq,
                    new RequestAddition())) {
                System.out.println("service_chat_stream:");
                for (ServiceChatResponse item : stream) {
                    if (item != null && item.getData() != null && item.getData().getGeneratedAnswer() != null) {
                        System.out.print(item.getData().getGeneratedAnswer());
                    }
                    if (item != null && item.getData() != null && Boolean.TRUE.equals(item.getData().getEnd())) {
                        break;
                    }
                }
                System.out.print("\n");
            }
        }

        RerankRequest rrReq = RerankRequest.builder()
                .datas(Arrays.asList(
                        RerankDataItem.builder().query("2025 Q1 revenue growth")
                                .content("Revenue grew 12% YoY to $3.4B.").title("Revenue").build(),
                        RerankDataItem.builder().query("2025 Q1 revenue growth")
                                .content("Operating margin improved by 1.5pp to 17%.").title("Margin").build()))
                .build();
        RerankResponse rrResp = service.rerank(rrReq, new RequestAddition());
        ExampleUtil.printJson("rerank", rrResp);
    }
}
