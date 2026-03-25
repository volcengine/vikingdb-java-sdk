// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.api;

import com.volcengine.vikingdb.runtime.enums.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiInfo {
    private final String name;
    private final Method method;
    private final String path;

    public static final ApiInfo RERANK = new ApiInfo("Rerank", Method.POST, "/api/knowledge/service/rerank");

    public static final ApiInfo CHAT_COMPLETIONS = new ApiInfo("ChatCompletions", Method.POST, "/api/knowledge/chat/completions");
    public static final ApiInfo SERVICE_CHAT = new ApiInfo("ServiceChat", Method.POST, "/api/knowledge/service/chat");

    public static final ApiInfo ADD_DOC = new ApiInfo("AddDoc", Method.POST, "/api/knowledge/doc/add");
    public static final ApiInfo ADD_DOC_V2 = new ApiInfo("AddDocV2", Method.POST, "/api/knowledge/doc/v2/add");
    public static final ApiInfo DELETE_DOC = new ApiInfo("DeleteDoc", Method.POST, "/api/knowledge/doc/delete");
    public static final ApiInfo GET_DOC = new ApiInfo("GetDoc", Method.POST, "/api/knowledge/doc/info");
    public static final ApiInfo LIST_DOCS = new ApiInfo("ListDocs", Method.POST, "/api/knowledge/doc/list");
    public static final ApiInfo UPDATE_DOC_META = new ApiInfo("UpdateDocMeta", Method.POST, "/api/knowledge/doc/update_meta");
    public static final ApiInfo UPDATE_DOC = new ApiInfo("UpdateDoc", Method.POST, "/api/knowledge/doc/update");

    public static final ApiInfo GET_POINT = new ApiInfo("GetPoint", Method.POST, "/api/knowledge/point/info");
    public static final ApiInfo LIST_POINTS = new ApiInfo("ListPoints", Method.POST, "/api/knowledge/point/list");
    public static final ApiInfo ADD_POINT = new ApiInfo("AddPoint", Method.POST, "/api/knowledge/point/add");
    public static final ApiInfo UPDATE_POINT = new ApiInfo("UpdatePoint", Method.POST, "/api/knowledge/point/update");
    public static final ApiInfo DELETE_POINT = new ApiInfo("DeletePoint", Method.POST, "/api/knowledge/point/delete");

    public static final ApiInfo SEARCH_COLLECTION = new ApiInfo("SearchCollection", Method.POST, "/api/knowledge/collection/search");
    public static final ApiInfo SEARCH_KNOWLEDGE = new ApiInfo("SearchKnowledge", Method.POST, "/api/knowledge/collection/search_knowledge");
}

