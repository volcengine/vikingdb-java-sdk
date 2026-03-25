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
public class SearchKnowledgeRequest {
    @JsonProperty("query")
    private String query;

    @JsonProperty("image_query")
    private String imageQuery;

    @JsonProperty("pre_processing")
    private Map<String, Object> preProcessing;

    @JsonProperty("post_processing")
    private Map<String, Object> postProcessing;

    @JsonProperty("query_param")
    private Map<String, Object> queryParam;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("dense_weight")
    private Double denseWeight;
}

