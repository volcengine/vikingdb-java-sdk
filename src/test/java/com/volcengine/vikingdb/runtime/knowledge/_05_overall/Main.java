package com.volcengine.vikingdb.runtime.knowledge._05_overall;

import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.knowledge.model.request.AddDocV2Request;
import com.volcengine.vikingdb.runtime.knowledge.model.request.SearchKnowledgeRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.response.AddDocResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.ChatCompletionResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.SearchKnowledgeResponse;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeCollectionClient;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeService;
import com.volcengine.vikingdb.runtime.knowledge.util.ExampleUtil;

import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        KnowledgeService service = ExampleUtil.newKnowledgeService(ExampleUtil.preferAuth());
        KnowledgeCollectionClient kc = service.collection(ExampleUtil.defaultCollectionMeta());

        String docId = ExampleUtil.envOrDefault("VIKING_DOC_ID", "java-overall-doc-" + UUID.randomUUID());
        String uri = ExampleUtil.envOrDefault("VIKING_DOC_URI", "tos://your-bucket/your-path/your-file.pdf");

        AddDocV2Request addReq = AddDocV2Request.builder()
                .docId(docId)
                .docName(ExampleUtil.envOrDefault("VIKING_DOC_NAME", "your-file-name.pdf"))
                .uri(uri)
                .build();
        AddDocResponse addResp = kc.addDocV2(addReq, new RequestAddition().setRequestId("knowledge_overall_add_doc_java_example"));
        ExampleUtil.printJson("overall_add_doc_v2", addResp);

        SearchKnowledgeRequest skReq = SearchKnowledgeRequest.builder()
                .query(ExampleUtil.envOrDefault("VIKING_QUERY", "your query"))
                .limit(10)
                .denseWeight(0.5)
                .build();
        SearchKnowledgeResponse skResp = kc.searchKnowledge(skReq, new RequestAddition().setRequestId("knowledge_overall_search_knowledge_java_example"));
        ExampleUtil.printJson("overall_search_knowledge", skResp);

        ChatCompletionResponse chatResp = ExampleUtil.chatCompletion(service, skResp, skReq.getQuery());
        ExampleUtil.printJson("overall_chat_completion", chatResp);
    }
}

