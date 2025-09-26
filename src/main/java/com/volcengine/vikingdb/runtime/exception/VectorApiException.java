// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.exception;

import com.volcengine.vikingdb.runtime.core.ResponseContext;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VectorApiException extends Exception {
    protected int httpStatusCode;
    protected String apiName;
    protected String code;
    protected String message;
    protected String requestId;
    protected ResponseContext responseContext;

    @Override
    public String toString() {
        return "VectorApiException{" +
                "httpStatusCode=" + httpStatusCode +
                ", apiName='" + apiName + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", requestId='" + requestId + '\'' +
                ", responseContext=" + responseContext +
                '}';
    }

    @Override
    public String getMessage() {
        return toString();
    }
}
