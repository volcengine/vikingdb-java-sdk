// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class SearchKnowledgeResult {
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("rewrite_query")
    private String rewriteQuery;

    @JsonProperty("token_usage")
    private Map<String, Object> tokenUsage;

    @JsonProperty("result_list")
    private List<PointInfo> resultList;
}

