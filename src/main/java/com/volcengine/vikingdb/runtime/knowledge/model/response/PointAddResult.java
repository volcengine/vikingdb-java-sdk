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
public class PointAddResult {
    @JsonProperty("collection_name")
    private String collectionName;

    @JsonProperty("project")
    private String project;

    @JsonProperty("resource_id")
    private String resourceId;

    @JsonProperty("doc_id")
    private String docId;

    @JsonProperty("chunk_id")
    private Long chunkId;

    @JsonProperty("point_id")
    private String pointId;
}

