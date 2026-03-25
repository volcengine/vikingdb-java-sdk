// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class DedupOptions {
    @JsonProperty("content_dedup")
    private Boolean contentDedup;

    @JsonProperty("doc_name_dedup")
    private Boolean docNameDedup;

    @JsonProperty("auto_skip")
    private Boolean autoSkip;
}

