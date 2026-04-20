package com.volcengine.vikingdb.runtime.knowledge._05_overall;

import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.core.auth.Auth;
import com.volcengine.vikingdb.runtime.knowledge.model.request.AddDocV2Request;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ChatMessage;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ServiceChatRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.response.AddDocResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.ServiceChatResponse;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeCollectionClient;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeService;
import com.volcengine.vikingdb.runtime.knowledge.util.ExampleUtil;

import java.util.Collections;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        Auth auth = ExampleUtil.apiKeyAuthFromEnv("VIKING_API_KEY");
        if (auth == null) {
            throw new IllegalArgumentException("missing required env: VIKING_API_KEY");
        }
        KnowledgeService service = ExampleUtil.newKnowledgeService(auth);
        KnowledgeCollectionClient kc = service.collection(ExampleUtil.defaultCollectionMeta());

        String docId = ExampleUtil.envOrDefault("VIKING_DOC_ID", "java-overall-doc-" + UUID.randomUUID());
        String uri = ExampleUtil.envOrDefault("VIKING_DOC_URI", "tos://your-bucket/your-path/your-file.pdf");

        AddDocV2Request addReq = AddDocV2Request.builder()
                .docId(docId)
                .docName(ExampleUtil.envOrDefault("VIKING_DOC_NAME", "your-file-name.pdf"))
                .uri(uri)
                .build();
        AddDocResponse addResp = kc.addDocV2(addReq, new RequestAddition());
        ExampleUtil.printJson("overall_add_doc_v2", addResp);

        ServiceChatRequest serviceChatReq = ServiceChatRequest.builder()
                .serviceResourceId(ExampleUtil.envOrDefault("VIKING_SERVICE_RID", ""))
                .messages(Collections.singletonList(
                        ChatMessage.builder()
                                .role("user")
                                .content(ExampleUtil.envOrDefault("VIKING_QUERY", "your query"))
                                .build()))
                .stream(false)
                .build();
        ServiceChatResponse serviceChatResp = service.serviceChat(serviceChatReq,
                new RequestAddition());
        ExampleUtil.printJson("overall_service_chat", serviceChatResp);
    }
}
