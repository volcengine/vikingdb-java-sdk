// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.core.auth;

import com.volcengine.vikingdb.runtime.enums.AuthType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class AuthWithApiKey implements Auth {
    private final String apiKey;

    public AuthWithApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public AuthType getAuthType() {
        return AuthType.API_KEY;
    }

}
