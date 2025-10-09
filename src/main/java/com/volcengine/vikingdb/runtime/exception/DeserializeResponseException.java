// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.exception;

public class DeserializeResponseException extends RuntimeException {
    public DeserializeResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
