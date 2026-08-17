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
public class VectorWeightedSumResult {
    @JsonProperty("vector")
    private List<Double> vector;

    @JsonProperty("found_count")
    private Long foundCount;

    @JsonProperty("found_ids")
    private List<Object> foundIds;

    @JsonProperty("weight_sum")
    private Double weightSum;
}
