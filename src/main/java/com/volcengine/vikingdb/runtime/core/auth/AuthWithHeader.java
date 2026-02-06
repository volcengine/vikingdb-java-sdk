// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.core.auth;

import com.volcengine.vikingdb.runtime.enums.AuthType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

/**
 * Header-based authentication provider.
 */
@Getter
@ToString
@EqualsAndHashCode
public class AuthWithHeader implements Auth {
    private final Map<String, String> headers;

    /**
     * Constructs a new AuthWithHeader with specified headers.
     *
     * @param headers The headers to be included in each request for authentication.
     */
    public AuthWithHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public AuthType getAuthType() {
        return AuthType.HEADER;
    }
}
