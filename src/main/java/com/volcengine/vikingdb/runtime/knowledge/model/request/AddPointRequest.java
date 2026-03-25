// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class AddPointRequest {
    @JsonProperty("doc_id")
    private String docId;

    @JsonProperty("chunk_type")
    private String chunkType;

    @JsonProperty("chunk_title")
    private String chunkTitle;

    @JsonProperty("content")
    private String content;

    @JsonProperty("question")
    private String question;

    @JsonProperty("fields")
    private List<Map<String, Object>> fields;
}

