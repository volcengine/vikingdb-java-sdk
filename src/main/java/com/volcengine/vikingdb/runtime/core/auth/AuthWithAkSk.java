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
public class AuthWithAkSk implements Auth {
    private final String ak;
    private final String sk;
    private final String sessionToken;

    public AuthWithAkSk(String ak, String sk) {
        this(ak, sk, null);
    }

    public AuthWithAkSk(String ak, String sk, String sessionToken) {
        this.ak = ak;
        this.sk = sk;
        this.sessionToken = sessionToken;
    }

    @Override
    public AuthType getAuthType() {
        return AuthType.AK_SK;
    }

}
