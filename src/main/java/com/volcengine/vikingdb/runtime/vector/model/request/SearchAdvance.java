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
@EqualsAndHashCode()
public class SearchAdvance {
    @JsonProperty("dense_weight")
    private List<Double> denseWeight;

    @JsonProperty("ids_in")
    private List<Object> idsIn;

    @JsonProperty("ids_not_in")
    private List<Object> idsNotIn;

    @JsonProperty("post_process_ops")
    private List<Map<String, Object>> postProcessOps;

    @JsonProperty("post_process_input_limit")
    private Integer postProcessInputLimit;

    @JsonProperty("scale_k")
    private Double scaleK;

    @JsonProperty("filter_pre_ann_limit")
    private Integer filterPreAnnLimit;

    @JsonProperty("filter_pre_ann_ratio")
    private Double filterPreAnnRatio;
}
