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
public class ServiceChatRetrieveItemDocInfo {
    @JsonProperty("doc_id")
    private String docId;

    @JsonProperty("doc_name")
    private String docName;

    @JsonProperty("create_time")
    private Long createTime;

    @JsonProperty("doc_type")
    private String docType;

    @JsonProperty("doc_meta")
    private String docMeta;

    @JsonProperty("source")
    private String source;

    @JsonProperty("title")
    private String title;
}

