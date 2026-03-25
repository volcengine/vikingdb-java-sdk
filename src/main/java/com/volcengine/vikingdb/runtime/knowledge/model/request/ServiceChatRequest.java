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
public class ServiceChatRequest {
    @JsonProperty("service_resource_id")
    private String serviceResourceId;

    @JsonProperty("messages")
    private List<ChatMessage> messages;

    @JsonProperty("query_param")
    private Map<String, Object> queryParam;

    @JsonProperty("stream")
    private Boolean stream;
}

