// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.core;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class RequestAddition {
    private String requestId;

    private Map<String, String> headers;

    private Map<String, String> params;

    public RequestAddition setRequestId(String requestId) {
        this.requestId = requestId;
        return addHeader(Const.HEADER_REQUEST_ID, requestId);
    }

    public RequestAddition addHeader(String key, String value) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
        return this;
    }

    public RequestAddition addHeaders(Map<String, String> headers) {
        if (this.headers == null) {
            this.headers = new HashMap<>();
        }
        this.headers.putAll(headers);
        return this;
    }

    public RequestAddition addParam(String key, String value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        this.params.put(key, value);
        return this;
    }

    public RequestAddition addParams(Map<String, String> params) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        this.params.putAll(params);
        return this;
    }
}
