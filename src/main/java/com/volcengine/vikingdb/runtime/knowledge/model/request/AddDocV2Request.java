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
public class AddDocV2Request {
    @JsonProperty("doc_id")
    private String docId;

    @JsonProperty("doc_name")
    private String docName;

    @JsonProperty("doc_type")
    private String docType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("tag_list")
    private List<MetaItem> tagList;

    @JsonProperty("uri")
    private String uri;
}

