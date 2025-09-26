// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
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
    @JsonProperty("keywords")
    private List<String> keywords;

    @JsonProperty("case_sensitive")
    private Boolean caseSensitive;
}
