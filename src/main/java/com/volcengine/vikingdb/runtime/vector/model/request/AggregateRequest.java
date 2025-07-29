// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.vector.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class AggregateRequest extends BaseIndexRequest {

    @JsonProperty("filter")
    private Map<String, Object> filter;

    @JsonProperty("op")
    private String op;

    @JsonProperty("field")
    private String field;

    @JsonProperty("cond")
    private Map<String, Object> cond;
}
