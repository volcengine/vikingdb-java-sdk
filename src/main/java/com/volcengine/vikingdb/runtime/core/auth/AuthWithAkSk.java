// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
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

    public AuthWithAkSk(String ak, String sk) {
        this.ak = ak;
        this.sk = sk;
    }

    @Override
    public AuthType getAuthType() {
        return AuthType.AK_SK;
    }

}
