// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModelRerank {
    @JsonProperty("model_name")
    private String modelName;

    @JsonProperty("model_version")
    private String modelVersion;

    @JsonProperty("instruction")
    private String instruction;

    @JsonProperty("input_limit")
    private Integer inputLimit;

    @JsonProperty("score_threshold")
    private Double scoreThreshold;

    @JsonProperty("fail_strategy")
    private String failStrategy;

    @JsonProperty("timeout_ms")
    private Integer timeoutMs;
}
