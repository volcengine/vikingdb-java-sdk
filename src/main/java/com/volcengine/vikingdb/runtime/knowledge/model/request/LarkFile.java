// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.knowledge.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class LarkFile {
    @JsonProperty("url")
    private String url;

    @JsonProperty("obj_type")
    private String objType;

    @JsonProperty("obj_token")
    private String objToken;

    @JsonProperty("include_child")
    private Boolean includeChild;
}
