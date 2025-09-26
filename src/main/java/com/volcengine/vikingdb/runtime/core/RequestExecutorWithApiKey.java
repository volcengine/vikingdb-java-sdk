// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.core;

import com.volcengine.vikingdb.runtime.enums.Method;

import java.util.HashMap;
import java.util.Map;

public class RequestExecutorWithApiKey extends RequestExecutorWithNoAuth implements RequestExecutor {

    private final String apiKey;

    public RequestExecutorWithApiKey(ApiClient apiClient, String apiKey) {
        super(apiClient);
        this.apiKey = apiKey;
    }

    @Override
    public ResponseContext execute(Method method, String path, Map<String, String> headers, Map<String, String> params, String body) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Authorization", "Bearer " + apiKey);
        return super.execute(method, path, headers, params, body);
    }

}
