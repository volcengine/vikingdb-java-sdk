// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.exception;

/**
 * param requires non-empty, but it is empty now
 */
public class ParamEmptyException extends RuntimeException {
    public ParamEmptyException(String paramName) {
        super(String.format("param '%s' should not be empty", paramName));
    }
}
