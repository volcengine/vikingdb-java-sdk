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
public class ChatCompletionResult {
    @JsonProperty("reasoning_content")
    private String reasoningContent;

    @JsonProperty("generated_answer")
    private String generatedAnswer;

    @JsonProperty("usage")
    private String usage;

    @JsonProperty("prompt")
    private String prompt;

    @JsonProperty("model")
    private String model;

    @JsonProperty("finish_reason")
    private String finishReason;

    @JsonProperty("total_tokens")
    private Object totalTokens;
}

