// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TensorRerank {
    @JsonProperty("tensor")
    private List<List<Double>> tensor;

    @JsonProperty("input_limit")
    private Integer inputLimit;

    @JsonProperty("max_similarity_algo")
    private String maxSimilarityAlgo;
}
