// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.core;

import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.exception.BuildUriException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Request executor that adds custom headers to each request for authentication.
 */
public class RequestExecutorWithHeader implements RequestExecutor {

    private final ApiClient apiClient;
    private final Map<String, String> authHeaders;

    public RequestExecutorWithHeader(ApiClient apiClient, Map<String, String> authHeaders) {
        this.apiClient = apiClient;
        this.authHeaders = authHeaders != null ? authHeaders : new HashMap<>();
    }

    @Override
    public ResponseContext execute(Method method, String path, Map<String, String> headers, Map<String, String> params, String body) {
        long startTime = System.currentTimeMillis();
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(apiClient.getScheme().toString())
                .setHost(apiClient.getHost())
                .setPath(path);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }
        }
        URI uri;
        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new BuildUriException(e.getMessage(), e);
        }

        HttpRequestBase request = RequestExecutor.buildRequest(method, uri, body);
        
        // Create a new map to avoid modifying the input headers map
        Map<String, String> finalHeaders = new HashMap<>();
        if (headers != null) {
            finalHeaders.putAll(headers);
        }
        
        // Add auth headers, ensuring they don't overwrite existing headers if any (optional policy)
        // Usually, auth headers should be added.
        finalHeaders.putAll(authHeaders);
        
        RequestExecutor.setHeaders(request, finalHeaders);
        return RequestExecutor.getResponse(request, apiClient, startTime);
    }
}
