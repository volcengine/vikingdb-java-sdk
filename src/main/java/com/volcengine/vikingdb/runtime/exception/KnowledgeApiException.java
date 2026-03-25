// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.exception;

import com.volcengine.vikingdb.runtime.core.ResponseContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KnowledgeApiException extends Exception {
    protected int httpStatusCode;
    protected String apiName;
    protected Object code;
    protected String message;
    protected String requestId;
    protected ResponseContext responseContext;

    @Override
    public String toString() {
        return "KnowledgeApiException{" +
                "httpStatusCode=" + httpStatusCode +
                ", apiName='" + apiName + '\'' +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", requestId='" + requestId + '\'' +
                ", responseContext=" + responseContext +
                '}';
    }
}

