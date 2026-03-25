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
public class ServiceChatRetrieveItem {
    @JsonProperty("id")
    private String id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("md_content")
    private String mdContent;

    @JsonProperty("score")
    private Double score;

    @JsonProperty("point_id")
    private String pointId;

    @JsonProperty("origin_text")
    private String originText;

    @JsonProperty("original_question")
    private String originalQuestion;

    @JsonProperty("chunk_title")
    private String chunkTitle;

    @JsonProperty("chunk_id")
    private Long chunkId;

    @JsonProperty("process_time")
    private Long processTime;

    @JsonProperty("rerank_score")
    private Double rerankScore;

    @JsonProperty("doc_info")
    private ServiceChatRetrieveItemDocInfo docInfo;

    @JsonProperty("recall_position")
    private Integer recallPosition;

    @JsonProperty("rerank_position")
    private Integer rerankPosition;

    @JsonProperty("chunk_type")
    private String chunkType;

    @JsonProperty("chunk_source")
    private String chunkSource;

    @JsonProperty("update_time")
    private Long updateTime;

    @JsonProperty("chunk_attachment")
    private List<ChunkAttachment> chunkAttachment;

    @JsonProperty("table_chunk_fields")
    private List<PointTableChunkField> tableChunkFields;

    @JsonProperty("original_coordinate")
    private Map<String, Object> originalCoordinate;
}

