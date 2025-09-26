// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.core.auth;

import com.volcengine.vikingdb.runtime.enums.AuthType;

public interface Auth {
    AuthType getAuthType();
}
