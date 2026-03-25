// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.model.response;

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
public class PointInfo {
    @JsonProperty("collection_name")
    private String collectionName;

    @JsonProperty("point_id")
    private String pointId;

    @JsonProperty("process_time")
    private Long processTime;

    @JsonProperty("origin_text")
    private String originText;

    @JsonProperty("md_content")
    private String mdContent;

    @JsonProperty("html_content")
    private String htmlContent;

    @JsonProperty("chunk_title")
    private String chunkTitle;

    @JsonProperty("chunk_type")
    private String chunkType;

    @JsonProperty("content")
    private String content;

    @JsonProperty("chunk_id")
    private Long chunkId;

    @JsonProperty("original_question")
    private String originalQuestion;

    @JsonProperty("doc_info")
    private PointDocInfo docInfo;

    @JsonProperty("rerank_score")
    private Double rerankScore;

    @JsonProperty("score")
    private Double score;

    @JsonProperty("chunk_source")
    private String chunkSource;

    @JsonProperty("chunk_attachment")
    private List<ChunkAttachment> chunkAttachment;

    @JsonProperty("table_chunk_fields")
    private List<PointTableChunkField> tableChunkFields;

    @JsonProperty("update_time")
    private Long updateTime;

    @JsonProperty("description")
    private String description;

    @JsonProperty("chunk_status")
    private String chunkStatus;

    @JsonProperty("video_frame")
    private String videoFrame;

    @JsonProperty("video_url")
    private String videoUrl;

    @JsonProperty("video_start_time")
    private Long videoStartTime;

    @JsonProperty("video_end_time")
    private Long videoEndTime;

    @JsonProperty("video_outline")
    private Map<String, Object> videoOutline;

    @JsonProperty("audio_start_time")
    private Long audioStartTime;

    @JsonProperty("audio_end_time")
    private Long audioEndTime;

    @JsonProperty("audio_outline")
    private Map<String, Object> audioOutline;

    @JsonProperty("sheet_name")
    private String sheetName;

    @JsonProperty("project")
    private String project;

    @JsonProperty("resource_id")
    private String resourceId;
}

