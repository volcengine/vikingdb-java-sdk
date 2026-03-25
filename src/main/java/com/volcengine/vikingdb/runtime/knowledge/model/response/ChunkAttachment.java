// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class ChunkAttachment {
    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("caption")
    private String caption;

    @JsonProperty("type")
    private String type;

    @JsonProperty("link")
    private String link;

    @JsonProperty("info_link")
    private String infoLink;

    @JsonProperty("column_name")
    private String columnName;
}

