// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AggregateResult {
    @JsonProperty("agg")
    private Map<String, Object> agg;

    @JsonProperty("op")
    private String op;

    /**
     * @deprecated use {@link #groupBy} instead.
     */
    @Deprecated
    @JsonProperty("field")
    private String field;

    @JsonProperty("group_by")
    private String groupBy;

    @JsonProperty("calc_value_on")
    private String calcValueOn;

    @JsonProperty("vector_weighted_sum")
    private VectorWeightedSumResult vectorWeightedSum;
}
