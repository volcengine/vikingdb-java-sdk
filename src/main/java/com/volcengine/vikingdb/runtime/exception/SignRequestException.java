// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.exception;

public class SignRequestException extends RuntimeException {
    public SignRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
