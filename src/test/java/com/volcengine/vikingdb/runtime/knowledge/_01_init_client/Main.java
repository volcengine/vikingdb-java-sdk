package com.volcengine.vikingdb.runtime.knowledge._01_init_client;

import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeCollectionClient;
import com.volcengine.vikingdb.runtime.knowledge.service.KnowledgeService;
import com.volcengine.vikingdb.runtime.knowledge.util.ExampleUtil;

public class Main {
    public static void main(String[] args) {
        KnowledgeService service = ExampleUtil.newKnowledgeService(ExampleUtil.preferAuth());
        KnowledgeCollectionClient collectionClient = service.collection(ExampleUtil.defaultCollectionMeta());
        System.out.println("knowledge_service: " + service.getClass().getSimpleName());
        System.out.println("collection_client: " + (collectionClient == null ? "null" : collectionClient.getClass().getSimpleName()));
    }
}

