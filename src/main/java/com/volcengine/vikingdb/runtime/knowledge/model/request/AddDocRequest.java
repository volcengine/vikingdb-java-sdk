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
public class AddDocRequest {
    @JsonProperty("add_type")
    private String addType;

    @JsonProperty("doc_id")
    private String docId;

    @JsonProperty("doc_name")
    private String docName;

    @JsonProperty("doc_type")
    private String docType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("tos_path")
    private String tosPath;

    @JsonProperty("url")
    private String url;

    @JsonProperty("lark_file")
    private String larkFile;

    @JsonProperty("meta")
    private List<MetaItem> meta;

    @JsonProperty("dedup")
    private DedupOptions dedup;
}

