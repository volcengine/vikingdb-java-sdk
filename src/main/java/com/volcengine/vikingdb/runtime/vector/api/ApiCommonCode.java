// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.vector.api;

/**
 * Some common success or error code.
 * Note: The following codes do not represent all possible codes
 * that may occur in the service.
 */
public class ApiCommonCode {
    public static final String SUCCESS = "Success";
    public static final String ERROR_MISSING_PARAMETER = "MissingParameter";
    public static final String ERROR_INVALID_PARAMETER = "InvalidParameter";
    public static final String ERROR_ACCESS_DENIED = "AccessDenied";
    public static final String ERROR_INTERNAL_ERROR = "InternalError";
    public static final String ERROR_TOO_MANY_REQUESTS = "TooManyRequests";
}
