// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.core;

import com.volcengine.service.SignableRequest;
import com.volcengine.vikingdb.runtime.core.auth.Sign;
import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.exception.BuildUriException;
import com.volcengine.vikingdb.runtime.exception.SignRequestException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.util.Map;

public class RequestExecutorWithAkSk implements RequestExecutor {

    private final ApiClient apiClient;
    private final String ak;
    private final String sk;

    public RequestExecutorWithAkSk(ApiClient apiClient, String ak, String sk) {
        this.apiClient = apiClient;
        this.ak = ak;
        this.sk = sk;
    }

    @Override
    public ResponseContext execute(Method method, String path, Map<String, String> headers, Map<String, String> params, String body)  {
        long startTime = System.currentTimeMillis();

        SignableRequest signableRequest;
        try {
            signableRequest = Sign.prepareRequest(
                    apiClient.getScheme(), apiClient.getHost(), path, method,
                    params, headers, body, ak, sk, apiClient.getRegion()
            );
        } catch (Exception e) {
            throw new SignRequestException(e.getMessage(), e);
        }

        URI uri;
        try {
            uri = new URIBuilder()
                    .setScheme(apiClient.getScheme().toString())
                    .setHost(apiClient.getHost())
                    .setPath(path)
                    .build();
        } catch (Exception e) {
            throw new BuildUriException(e.getMessage(), e);
        }

        HttpRequestBase request = RequestExecutor.buildRequest(method, uri, body);
        request.setConfig(signableRequest.getConfig());
        RequestExecutor.setHeaders(request, signableRequest.getAllHeaders());
        return RequestExecutor.getResponse(request, apiClient, startTime);
    }

}
