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
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseSearchRequest extends BaseIndexRequest {
    @JsonProperty("output_fields")
    private List<String> outputFields;

    @JsonProperty("filter")
    private Map<String, Object> filter;

    @JsonProperty("partition")
    private String partition;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("return_schema")
    private Boolean returnSchema;

    @JsonProperty("return_download_url")
    private Boolean returnDownloadUrl;

    @JsonProperty("return_analyzed_result")
    private Boolean returnAnalyzedResult;

    @JsonProperty("return_detail_info")
    private Boolean returnDetailInfo;

    @JsonProperty("advance")
    private SearchAdvance advance;
}
