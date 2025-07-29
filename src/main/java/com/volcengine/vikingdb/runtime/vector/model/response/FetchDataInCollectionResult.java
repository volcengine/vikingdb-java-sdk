// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.vector.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FetchDataInCollectionResult {
    @JsonProperty("fetch")
    private List<FetchInCollectionItem> fetch;

    @JsonProperty("ids_not_exist")
    private List<Object> idsNotExist;
}
