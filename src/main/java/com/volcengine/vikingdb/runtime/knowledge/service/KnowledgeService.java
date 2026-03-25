// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.service.SignableRequest;
import com.volcengine.vikingdb.runtime.Util;
import com.volcengine.vikingdb.runtime.Version;
import com.volcengine.vikingdb.runtime.core.*;
import com.volcengine.vikingdb.runtime.core.auth.Auth;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithApiKey;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithHeader;
import com.volcengine.vikingdb.runtime.core.auth.Sign;
import com.volcengine.vikingdb.runtime.enums.AuthType;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.exception.*;
import com.volcengine.vikingdb.runtime.knowledge.api.ApiInfo;
import com.volcengine.vikingdb.runtime.knowledge.api.ApiRequestBinding;
import com.volcengine.vikingdb.runtime.knowledge.model.CollectionMeta;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ChatCompletionRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.RerankRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.request.ServiceChatRequest;
import com.volcengine.vikingdb.runtime.knowledge.model.response.BaseResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.ChatCompletionResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.RerankResponse;
import com.volcengine.vikingdb.runtime.knowledge.model.response.ServiceChatResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class KnowledgeService {
    private static final String DEFAULT_SERVICE_NAME = "air";

    protected final ApiClient apiClient;

    public KnowledgeService(String host, Auth auth) {
        this(null, host, null, auth, null);
    }

    public KnowledgeService(Scheme scheme, String host, Auth auth) {
        this(scheme, host, null, auth, null);
    }

    public KnowledgeService(Scheme scheme, String host, String region, Auth auth) {
        this(scheme, host, region, auth, null);
    }

    public KnowledgeService(Scheme scheme, String host, String region, Auth auth, ClientConfig clientConfig) {
        this.apiClient = new ApiClient(scheme, host, region, auth, clientConfig, DEFAULT_SERVICE_NAME);
    }

    public KnowledgeCollectionClient collection(CollectionMeta meta) {
        return new KnowledgeCollectionClient(apiClient, meta);
    }

    public RerankResponse rerank(RerankRequest request) throws ApiClientException, KnowledgeApiException {
        return rerank(request, null);
    }

    public RerankResponse rerank(RerankRequest request, RequestAddition addition)
            throws ApiClientException, KnowledgeApiException {
        return new ApiRequestBinding<>(
                ApiInfo.RERANK,
                request,
                new TypeReference<RerankResponse>() {
                }).callApi(apiClient, addition);
    }

    public ChatCompletionResponse chatCompletion(ChatCompletionRequest request)
            throws ApiClientException, KnowledgeApiException {
        return chatCompletion(request, null);
    }

    public ChatCompletionResponse chatCompletion(ChatCompletionRequest request, RequestAddition addition)
            throws ApiClientException, KnowledgeApiException {
        if (request != null && Boolean.TRUE.equals(request.getStream())) {
            throw new IllegalArgumentException("stream=true: use chatCompletionStream");
        }
        return new ApiRequestBinding<>(
                ApiInfo.CHAT_COMPLETIONS,
                request,
                new TypeReference<ChatCompletionResponse>() {
                }).callApi(apiClient, addition);
    }

    public ServiceChatResponse serviceChat(ServiceChatRequest request)
            throws ApiClientException, KnowledgeApiException {
        return serviceChat(request, null);
    }

    public ServiceChatResponse serviceChat(ServiceChatRequest request, RequestAddition addition)
            throws ApiClientException, KnowledgeApiException {
        if (request != null && Boolean.TRUE.equals(request.getStream())) {
            throw new IllegalArgumentException("stream=true: use serviceChatStream");
        }
        return new ApiRequestBinding<>(
                ApiInfo.SERVICE_CHAT,
                request,
                new TypeReference<ServiceChatResponse>() {
                }).callApi(apiClient, addition);
    }

    public KnowledgeStream<ChatCompletionResponse> chatCompletionStream(ChatCompletionRequest request)
            throws ApiClientException, KnowledgeApiException {
        return chatCompletionStream(request, null);
    }

    public KnowledgeStream<ChatCompletionResponse> chatCompletionStream(ChatCompletionRequest request,
            RequestAddition addition) throws ApiClientException, KnowledgeApiException {
        if (request == null) {
            request = new ChatCompletionRequest();
        }
        if (!Boolean.TRUE.equals(request.getStream())) {
            request = request.toBuilder().stream(true).build();
        }
        return postStream(ApiInfo.CHAT_COMPLETIONS, request, addition, ChatCompletionResponse.class);
    }

    public KnowledgeStream<ServiceChatResponse> serviceChatStream(ServiceChatRequest request)
            throws ApiClientException, KnowledgeApiException {
        return serviceChatStream(request, null);
    }

    public KnowledgeStream<ServiceChatResponse> serviceChatStream(ServiceChatRequest request, RequestAddition addition)
            throws ApiClientException, KnowledgeApiException {
        if (request == null) {
            request = new ServiceChatRequest();
        }
        if (!Boolean.TRUE.equals(request.getStream())) {
            request = request.toBuilder().stream(true).build();
        }
        return postStream(ApiInfo.SERVICE_CHAT, request, addition, ServiceChatResponse.class);
    }

    private <T> KnowledgeStream<T> postStream(ApiInfo apiInfo, Object requestBody, RequestAddition addition,
            Class<T> itemClass)
            throws ApiClientException, KnowledgeApiException {
        String body;
        try {
            body = ApiClient.objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new SerializeRequestException(e.getMessage(), e.getCause());
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(Const.HEADER_USER_AGENT, String.format(Const.USER_AGENT_FORMAT, Version.getVersion()));
        Map<String, String> params = null;
        if (addition != null) {
            if (addition.getHeaders() != null) {
                headers.putAll(addition.getHeaders());
            }
            params = addition.getParams();
        }

        HttpRequestBase httpRequest;
        Auth auth = apiClient.getAuth();
        if (auth != null && auth.getAuthType() == AuthType.AK_SK) {
            AuthWithAkSk akSk = (AuthWithAkSk) auth;
            SignableRequest signableRequest;
            try {
                signableRequest = Sign.prepareRequest(
                        apiClient.getScheme(),
                        apiClient.getHost(),
                        apiInfo.getPath(),
                        apiInfo.getMethod(),
                        params,
                        headers,
                        body,
                        akSk.getAk(),
                        akSk.getSk(),
                        apiClient.getRegion(),
                        apiClient.getServiceName());
            } catch (Exception e) {
                throw new SignRequestException(e.getMessage(), e);
            }
            URI uri;
            try {
                uri = signableRequest.getUriBuilder().build();
            } catch (Exception e) {
                throw new BuildUriException(e.getMessage(), e);
            }
            HttpPost post = new HttpPost(uri);
            post.setConfig(signableRequest.getConfig());
            RequestExecutor.setHeaders(post, signableRequest.getAllHeaders());
            post.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            httpRequest = post;
        } else {
            URI uri;
            try {
                URIBuilder builder = new URIBuilder()
                        .setScheme(apiClient.getScheme().toString())
                        .setHost(apiClient.getHost())
                        .setPath(apiInfo.getPath());
                if (params != null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        builder.addParameter(entry.getKey(), entry.getValue());
                    }
                }
                uri = builder.build();
            } catch (Exception e) {
                throw new BuildUriException(e.getMessage(), e);
            }
            HttpPost post = new HttpPost(uri);
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            if (auth != null && auth.getAuthType() == AuthType.API_KEY) {
                AuthWithApiKey apiKey = (AuthWithApiKey) auth;
                headers.put("Authorization", "Bearer " + apiKey.getApiKey());
            } else if (auth != null && auth.getAuthType() == AuthType.HEADER) {
                AuthWithHeader headerAuth = (AuthWithHeader) auth;
                if (headerAuth.getHeaders() != null) {
                    headers.putAll(headerAuth.getHeaders());
                }
            }
            RequestExecutor.setHeaders(post, headers);
            post.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            httpRequest = post;
        }

        HttpResponse response;
        try {
            response = apiClient.getClientConfig().getHttpClient().execute(httpRequest);
        } catch (Exception e) {
            throw new ApiClientException(e.getMessage(), e);
        }
        int status = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();
        if (status < 200 || status >= 300) {
            String raw = "";
            try {
                raw = entity != null ? EntityUtils.toString(entity) : "";
            } catch (Exception ignored) {
            }
            ResponseContext responseContext = new ResponseContext(
                    status,
                    0L,
                    Util.getHeadersMap(response.getAllHeaders()),
                    raw);
            try {
                BaseResponse base = ApiClient.objectMapper.readValue(raw, BaseResponse.class);
                throw new KnowledgeApiException(status, apiInfo.getName(), base.getCode(), base.getMessage(),
                        base.getRequestId(), responseContext);
            } catch (KnowledgeApiException e) {
                throw e;
            } catch (Exception e) {
                throw new KnowledgeApiException(status, apiInfo.getName(), null, raw, null, responseContext);
            }
        }

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8));
            Closeable closeable = response instanceof Closeable ? (Closeable) response : () -> {
            };
            return new KnowledgeStream<>(reader, closeable, itemClass);
        } catch (Exception e) {
            if (response instanceof Closeable) {
                try {
                    ((Closeable) response).close();
                } catch (Exception ignored) {
                }
            }
            throw new ApiClientException(e.getMessage(), e);
        }
    }
}
