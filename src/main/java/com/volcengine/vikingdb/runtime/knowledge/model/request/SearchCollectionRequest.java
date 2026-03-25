// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class SearchCollectionRequest {
    @JsonProperty("query")
    private String query;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("dense_weight")
    private Double denseWeight;

    @JsonProperty("rerank_switch")
    private Boolean rerankSwitch;

    @JsonProperty("query_param")
    private Map<String, Object> queryParam;

    @JsonProperty("retrieve_count")
    private Integer retrieveCount;

    @JsonProperty("endpoint_id")
    private String endpointId;

    @JsonProperty("rerank_model")
    private String rerankModel;

    @JsonProperty("rerank_only_chunk")
    private Boolean rerankOnlyChunk;

    @JsonProperty("get_attachment_link")
    private Boolean getAttachmentLink;
}
