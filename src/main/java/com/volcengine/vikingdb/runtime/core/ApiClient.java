// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volcengine.vikingdb.runtime.Util;
import com.volcengine.vikingdb.runtime.Version;
import com.volcengine.vikingdb.runtime.core.auth.Auth;
import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApiClient {
    public static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);;

    private final Auth auth;
    private final Scheme scheme;
    private final String host;
    private final String region;
    private final ClientConfig clientConfig;
    private final RequestExecutor requestExecutor;

    public ApiClient(String host, Auth auth) {
        this(null, host, "", auth, null);
    }

    public ApiClient(Scheme scheme, String host, String region, Auth auth) {
        this(scheme, host, region, auth, null);
    }

    public ApiClient(Scheme scheme, String host, String region, Auth auth, ClientConfig clientConfig) {
        this.auth = auth;
        if (scheme == null) {
            scheme = Scheme.DEFAULT;
        }
        this.scheme = scheme;
        Util.requiresNonEmpty("host", host);
        this.host = Util.trimHostPrefix(host);
        if (Util.isEmpty(region)) {
            region = Const.DEFAULT_REGION;
        }
        this.region = region;
        if (clientConfig == null) {
            clientConfig = new ClientConfig();
        }
        this.clientConfig = clientConfig;
        this.requestExecutor = RequestExecutor.create(this, auth);
    }

    public ResponseContext doRequest(Method method, String path, String body) {
        return doRequest(method, path, body, null, null);
    }

    public ResponseContext doRequest(Method method, String path, String body, RequestAddition requestAddition) {
        if (requestAddition == null) {
            return doRequest(method, path, body, null, null);
        }
        return doRequest(method, path, body, requestAddition.getHeaders(), requestAddition.getParams());
    }

    private ResponseContext doRequest(Method method, String path, String body, Map<String, String> headers, Map<String, String> params)  {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(Const.HEADER_USER_AGENT, String.format(Const.USER_AGENT_FORMAT, Version.getVersion()));
        if (body == null) {
            body = "";
        }
        return requestExecutor.execute(method, path, headers, params, body);
    }
}
