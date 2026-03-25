// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class DocInfo {
    @JsonProperty("collection_name")
    private String collectionName;

    @JsonProperty("doc_name")
    private String docName;

    @JsonProperty("doc_id")
    private String docId;

    @JsonProperty("doc_hash")
    private String docHash;

    @JsonProperty("add_type")
    private String addType;

    @JsonProperty("doc_type")
    private String docType;

    @JsonProperty("doc_summary")
    private String docSummary;

    @JsonProperty("brief_summary")
    private String briefSummary;

    @JsonProperty("doc_size")
    private Integer docSize;

    @JsonProperty("description")
    private String description;

    @JsonProperty("create_time")
    private Long createTime;

    @JsonProperty("added_by")
    private String addedBy;

    @JsonProperty("update_time")
    private Long updateTime;

    @JsonProperty("url")
    private String url;

    @JsonProperty("tos_path")
    private String tosPath;

    @JsonProperty("point_num")
    private Integer pointNum;

    @JsonProperty("status")
    private DocStatus status;

    @JsonProperty("title")
    private String title;

    @JsonProperty("source")
    private String source;

    @JsonProperty("total_tokens")
    private Object totalTokens;

    @JsonProperty("doc_summary_tokens")
    private Integer docSummaryTokens;

    @JsonProperty("doc_premium_status")
    private DocPremiumStatus docPremiumStatus;

    @JsonProperty("meta")
    private String meta;

    @JsonProperty("labels")
    private Map<String, String> labels;

    @JsonProperty("video_outline")
    private Map<String, Object> videoOutline;

    @JsonProperty("audio_outline")
    private Map<String, Object> audioOutline;

    @JsonProperty("statistics")
    private Map<String, Object> statistics;

    @JsonProperty("project")
    private String project;

    @JsonProperty("resource_id")
    private String resourceId;
}

