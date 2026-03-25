// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.vikingdb.runtime.Util;
import com.volcengine.vikingdb.runtime.core.ApiClient;
import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.exception.ApiClientException;
import com.volcengine.vikingdb.runtime.exception.KnowledgeApiException;
import com.volcengine.vikingdb.runtime.knowledge.api.ApiInfo;
import com.volcengine.vikingdb.runtime.knowledge.api.ApiRequestBinding;
import com.volcengine.vikingdb.runtime.knowledge.model.CollectionMeta;
import com.volcengine.vikingdb.runtime.knowledge.model.request.*;
import com.volcengine.vikingdb.runtime.knowledge.model.response.*;

import java.util.HashMap;
import java.util.Map;

public class KnowledgeCollectionClient {
    private final ApiClient apiClient;
    private final CollectionMeta meta;

    public KnowledgeCollectionClient(ApiClient apiClient, CollectionMeta meta) {
        this.apiClient = apiClient;
        this.meta = meta;
    }

    private Map<String, Object> metaPayload() {
        Map<String, Object> payload = new HashMap<>();
        if (meta == null) {
            return payload;
        }
        if (!Util.isEmpty(meta.getCollectionName())) {
            payload.put("collection_name", meta.getCollectionName());
        }
        if (!Util.isEmpty(meta.getProjectName())) {
            payload.put("project", meta.getProjectName());
        }
        if (!Util.isEmpty(meta.getResourceId())) {
            payload.put("resource_id", meta.getResourceId());
        }
        return payload;
    }

    private Map<String, Object> mergePayload(Object request) {
        Map<String, Object> payload = new HashMap<>(metaPayload());
        if (request == null) {
            return payload;
        }
        Map<String, Object> requestMap = ApiClient.objectMapper.convertValue(request, new TypeReference<Map<String, Object>>() {
        });
        payload.putAll(requestMap);
        return payload;
    }

    public AddDocResponse addDoc(AddDocRequest request) throws ApiClientException, KnowledgeApiException {
        return addDoc(request, null);
    }

    public AddDocResponse addDoc(AddDocRequest request, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = mergePayload(request);
        return new ApiRequestBinding<>(
                ApiInfo.ADD_DOC,
                payload,
                new TypeReference<AddDocResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public AddDocResponse addDocV2(AddDocV2Request request) throws ApiClientException, KnowledgeApiException {
        return addDocV2(request, null);
    }

    public AddDocResponse addDocV2(AddDocV2Request request, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = mergePayload(request);
        return new ApiRequestBinding<>(
                ApiInfo.ADD_DOC_V2,
                payload,
                new TypeReference<AddDocResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public BaseResponse deleteDoc(String docId) throws ApiClientException, KnowledgeApiException {
        return deleteDoc(docId, null);
    }

    public BaseResponse deleteDoc(String docId, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = metaPayload();
        payload.put("doc_id", docId);
        return new ApiRequestBinding<>(
                ApiInfo.DELETE_DOC,
                payload,
                new TypeReference<BaseResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public DocInfo getDoc(String docId) throws ApiClientException, KnowledgeApiException {
        return getDoc(docId, false, null);
    }

    public DocInfo getDoc(String docId, boolean returnTokenUsage, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = metaPayload();
        payload.put("doc_id", docId);
        if (returnTokenUsage) {
            payload.put("return_token_usage", true);
        }
        DocInfoResponse response = new ApiRequestBinding<>(
                ApiInfo.GET_DOC,
                payload,
                new TypeReference<DocInfoResponse>() {
                }
        ).callApi(apiClient, addition);
        DocInfo data = response.getData();
        if (data == null) {
            return null;
        }
        if (data.getProject() == null && meta != null && !Util.isEmpty(meta.getProjectName())) {
            data.setProject(meta.getProjectName());
        }
        if (data.getResourceId() == null && meta != null && !Util.isEmpty(meta.getResourceId())) {
            data.setResourceId(meta.getResourceId());
        }
        return data;
    }

    public ListDocsResponse listDocs(ListDocsRequest request) throws ApiClientException, KnowledgeApiException {
        return listDocs(request, null);
    }

    public ListDocsResponse listDocs(ListDocsRequest request, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = mergePayload(request);
        return new ApiRequestBinding<>(
                ApiInfo.LIST_DOCS,
                payload,
                new TypeReference<ListDocsResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public BaseResponse updateDocMeta(String docId, java.util.List<MetaItem> meta) throws ApiClientException, KnowledgeApiException {
        return updateDocMeta(docId, meta, null);
    }

    public BaseResponse updateDocMeta(String docId, java.util.List<MetaItem> meta, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = metaPayload();
        payload.put("doc_id", docId);
        payload.put("meta", meta);
        return new ApiRequestBinding<>(
                ApiInfo.UPDATE_DOC_META,
                payload,
                new TypeReference<BaseResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public BaseResponse updateDoc(String docId, String docName) throws ApiClientException, KnowledgeApiException {
        return updateDoc(docId, docName, null);
    }

    public BaseResponse updateDoc(String docId, String docName, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = metaPayload();
        payload.put("doc_id", docId);
        payload.put("doc_name", docName);
        return new ApiRequestBinding<>(
                ApiInfo.UPDATE_DOC,
                payload,
                new TypeReference<BaseResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public PointInfo getPoint(String pointId) throws ApiClientException, KnowledgeApiException {
        return getPoint(pointId, false, null);
    }

    public PointInfo getPoint(String pointId, boolean getAttachmentLink, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = metaPayload();
        payload.put("point_id", pointId);
        if (getAttachmentLink) {
            payload.put("get_attachment_link", true);
        }
        PointInfoResponse response = new ApiRequestBinding<>(
                ApiInfo.GET_POINT,
                payload,
                new TypeReference<PointInfoResponse>() {
                }
        ).callApi(apiClient, addition);
        PointInfo data = response.getData();
        if (data == null) {
            return null;
        }
        if (data.getProject() == null && meta != null && !Util.isEmpty(meta.getProjectName())) {
            data.setProject(meta.getProjectName());
        }
        if (data.getResourceId() == null && meta != null && !Util.isEmpty(meta.getResourceId())) {
            data.setResourceId(meta.getResourceId());
        }
        return data;
    }

    public ListPointsResponse listPoints(ListPointsRequest request) throws ApiClientException, KnowledgeApiException {
        return listPoints(request, null);
    }

    public ListPointsResponse listPoints(ListPointsRequest request, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = mergePayload(request);
        return new ApiRequestBinding<>(
                ApiInfo.LIST_POINTS,
                payload,
                new TypeReference<ListPointsResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public PointAddResponse addPoint(AddPointRequest request) throws ApiClientException, KnowledgeApiException {
        return addPoint(request, null);
    }

    public PointAddResponse addPoint(AddPointRequest request, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = mergePayload(request);
        return new ApiRequestBinding<>(
                ApiInfo.ADD_POINT,
                payload,
                new TypeReference<PointAddResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public BaseResponse updatePoint(String pointId, UpdatePointRequest update) throws ApiClientException, KnowledgeApiException {
        return updatePoint(pointId, update, null);
    }

    public BaseResponse updatePoint(String pointId, UpdatePointRequest update, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = metaPayload();
        payload.put("point_id", pointId);
        if (update != null) {
            payload.put("chunk_title", update.getChunkTitle());
            payload.put("content", update.getContent());
            payload.put("question", update.getQuestion());
            if (update.getFields() != null && !update.getFields().isEmpty()) {
                payload.put("fields", update.getFields());
            }
        }
        return new ApiRequestBinding<>(
                ApiInfo.UPDATE_POINT,
                payload,
                new TypeReference<BaseResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public BaseResponse deletePoint(DeletePointRequest request) throws ApiClientException, KnowledgeApiException {
        return deletePoint(request, null);
    }

    public BaseResponse deletePoint(DeletePointRequest request, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = metaPayload();
        if (request != null) {
            payload.put("point_id", request.getPointId());
        }
        return new ApiRequestBinding<>(
                ApiInfo.DELETE_POINT,
                payload,
                new TypeReference<BaseResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public SearchResponse searchCollection(SearchCollectionRequest request) throws ApiClientException, KnowledgeApiException {
        return searchCollection(request, null);
    }

    public SearchResponse searchCollection(SearchCollectionRequest request, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = mergePayload(request);
        if (meta != null && !Util.isEmpty(meta.getCollectionName())) {
            payload.put("name", meta.getCollectionName());
        }
        return new ApiRequestBinding<>(
                ApiInfo.SEARCH_COLLECTION,
                payload,
                new TypeReference<SearchResponse>() {
                }
        ).callApi(apiClient, addition);
    }

    public SearchKnowledgeResponse searchKnowledge(SearchKnowledgeRequest request) throws ApiClientException, KnowledgeApiException {
        return searchKnowledge(request, null);
    }

    public SearchKnowledgeResponse searchKnowledge(SearchKnowledgeRequest request, RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        Map<String, Object> payload = mergePayload(request);
        if (meta != null && !Util.isEmpty(meta.getCollectionName())) {
            payload.put("name", meta.getCollectionName());
        }
        return new ApiRequestBinding<>(
                ApiInfo.SEARCH_KNOWLEDGE,
                payload,
                new TypeReference<SearchKnowledgeResponse>() {
                }
        ).callApi(apiClient, addition);
    }
}

