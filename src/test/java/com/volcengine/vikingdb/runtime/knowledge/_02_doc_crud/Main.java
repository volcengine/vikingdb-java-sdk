package com.volcengine.vikingdb.runtime.knowledge._02_doc_crud;

import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.knowledge.model.request.AddDocV2Request;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ListDocsFilter;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ListDocsRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ListDocsV2Request;
import com.volcengine.vikingdb.runtime.knowledge.model.request.SearchDocsByFilterRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.MetaItem;
import com.volcengine.vikingdb.runtime.knowledge.model.response.AddDocResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.BaseResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.DocInfo;
import com.volcengine.vikingdb.runtime.knowledge.model.response.ListDocsResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.ListDocsV2Response;
import com.volcengine.vikingdb.runtime.knowledge.model.response.SearchDocsByFilterResponse;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeCollectionClient;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeService;
import com.volcengine.vikingdb.runtime.knowledge.util.ExampleUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        KnowledgeService service = ExampleUtil.newKnowledgeService(ExampleUtil.preferAuth());
        KnowledgeCollectionClient kc = service.collection(ExampleUtil.defaultCollectionMeta());

        String docId = ExampleUtil.envOrDefault("VIKING_DOC_ID", "java-example-doc-" + UUID.randomUUID());
        String docName = "Java SDK Knowledge Doc Example";
        String docType = "pdf";
        String uri = ExampleUtil.envOrDefault("VIKING_DOC_URI",
                "https://pdf.dfcfw.com/pdf/H3_AP202504281663850212_1.pdf");

        List<MetaItem> tags = new ArrayList<>();
        tags.add(MetaItem.builder().fieldName("category").fieldType("string").fieldValue("financial_report").build());
        tags.add(MetaItem.builder().fieldName("year").fieldType("int64").fieldValue(2025).build());
        tags.add(MetaItem.builder().fieldName("quarter").fieldType("string").fieldValue("Q1").build());

        AddDocV2Request addReq = AddDocV2Request.builder()
                .docId(docId)
                .docName(docName)
                .docType(docType)
                .uri(uri)
                .tagList(tags)
                .build();
        AddDocResponse addResp = kc.addDocV2(addReq, new RequestAddition());
        ExampleUtil.printJson("add_doc_v2", addResp);

        DocInfo docInfo = kc.getDoc(docId, true, new RequestAddition());
        ExampleUtil.printJson("get_doc", docInfo);

        tags.add(MetaItem.builder().fieldName("updated_at").fieldType("int64")
                .fieldValue(System.currentTimeMillis() / 1000).build());
        BaseResponse updateMetaResp = kc.updateDocMeta(docId, tags, new RequestAddition());
        ExampleUtil.printJson("update_doc_meta", updateMetaResp);

        BaseResponse updateDocResp = kc.updateDoc(docId, docName + " (Updated)", new RequestAddition());
        ExampleUtil.printJson("update_doc", updateDocResp);

        ListDocsRequest listReq = ListDocsRequest.builder()
                .offset(0)
                .limit(10)
                .filter(ListDocsFilter.builder().docIdList(Collections.singletonList(docId)).build())
                .returnTokenUsage(true)
                .build();
        ListDocsResponse listResp = kc.listDocs(listReq, new RequestAddition());
        ExampleUtil.printJson("list_docs", listResp);

        ListDocsV2Request listV2Req = ListDocsV2Request.builder()
                .limit(2)
                .build();
        ListDocsV2Response listV2Resp = kc.listDocsV2(listV2Req, new RequestAddition());
        ExampleUtil.printJson("list_docs_v2", listV2Resp);

        if (listV2Resp.getData() != null && listV2Resp.getData().getNextToken() != null) {
            ListDocsV2Request nextPageReq = ListDocsV2Request.builder()
                    .limit(2)
                    .nextToken(listV2Resp.getData().getNextToken())
                    .build();
            ExampleUtil.printJson("list_docs_v2_next_page", kc.listDocsV2(nextPageReq, new RequestAddition()));
        }

        SearchDocsByFilterRequest filterReq = SearchDocsByFilterRequest.builder()
                .filter(new HashMap<String, Object>() {
                    {
                        put("op", "must");
                        put("field", "category");
                        put("conds", Collections.singletonList("financial_report"));
                    }
                })
                .limit(10)
                .build();
        SearchDocsByFilterResponse filterResp = kc.searchDocsByFilter(filterReq, new RequestAddition());
        ExampleUtil.printJson("search_docs_by_filter", filterResp);
    }
}
