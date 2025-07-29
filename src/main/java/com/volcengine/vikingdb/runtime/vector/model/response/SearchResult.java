// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.vector.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SearchResult {
    @JsonProperty("data")
    private List<SearchItem> data;

    @JsonProperty("filter_matched_count")
    private Integer filterMatchedCount;

    @JsonProperty("total_return_count")
    private Integer totalReturnCount;

    @JsonProperty("real_text_query")
    private String realTextQuery;

    @JsonProperty("token_usage")
    private Object tokenUsage;

}
