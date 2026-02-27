// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchByKeywordsRequest extends BaseSearchRequest {
    @JsonProperty("mode")
    private String mode;

    @JsonProperty("keywords")
    private List<String> keywords;

    @JsonProperty("query")
    private String query;

    @JsonProperty("case_sensitive")
    private Boolean caseSensitive;

    @JsonProperty("fields")
    private List<String> fields;

    @JsonProperty("bm25_k1")
    private Double bm25K1;

    @JsonProperty("bm25_b")
    private Double bm25B;
}
