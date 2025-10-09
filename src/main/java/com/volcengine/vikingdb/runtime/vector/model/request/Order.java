// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Order {
    @JsonProperty("asc")
    ASC,

    @JsonProperty("desc")
    DESC
}
