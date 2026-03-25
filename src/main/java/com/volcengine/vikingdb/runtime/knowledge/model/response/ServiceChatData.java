// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class ServiceChatData {
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("rewrite_query")
    private String rewriteQuery;

    @JsonProperty("token_usage")
    private Object tokenUsage;

    @JsonProperty("result_list")
    private List<ServiceChatRetrieveItem> resultList;

    @JsonProperty("generated_answer")
    private String generatedAnswer;

    @JsonProperty("reasoning_content")
    private String reasoningContent;

    @JsonProperty("prompt")
    private String prompt;

    @JsonProperty("end")
    private Boolean end;
}

