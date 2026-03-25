// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.model.request;

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
public class RerankRequest {
    @JsonProperty("datas")
    private List<RerankDataItem> datas;

    @JsonProperty("endpoint_id")
    private String endpointId;

    @JsonProperty("rerank_model")
    private String rerankModel;

    @JsonProperty("rerank_instruction")
    private String rerankInstruction;
}

