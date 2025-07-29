// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.vector.api;

import com.volcengine.vikingdb.runtime.enums.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiInfo {
    private final String name;
    private final Method method;
    private final String path;

    public static final ApiInfo PING = new ApiInfo("Ping", Method.POST, "/api/vikingdb/Ping");

    public static final ApiInfo UPSERT_DATA = new ApiInfo("UpsertData", Method.POST, "/api/vikingdb/data/upsert");
    public static final ApiInfo UPDATE_DATA = new ApiInfo("UpdateData", Method.POST, "/api/vikingdb/data/update");
    public static final ApiInfo FETCH_DATA_IN_COLLECTION = new ApiInfo("FetchDataInCollection", Method.POST, "/api/vikingdb/data/fetch_in_collection");
    public static final ApiInfo DELETE_DATA = new ApiInfo("DeleteData", Method.POST, "/api/vikingdb/data/delete");

    public static final ApiInfo SEARCH_BY_VECTOR = new ApiInfo("SearchByVector", Method.POST, "/api/vikingdb/data/search/vector");
    public static final ApiInfo SEARCH_BY_MULTIMODAL = new ApiInfo("SearchByMultiModal", Method.POST, "/api/vikingdb/data/search/multi_modal");
    public static final ApiInfo SEARCH_BY_ID = new ApiInfo("SearchById", Method.POST, "/api/vikingdb/data/search/id");
    public static final ApiInfo SEARCH_BY_SCALAR = new ApiInfo("SearchByScalar", Method.POST, "/api/vikingdb/data/search/scalar");
    public static final ApiInfo SEARCH_BY_KEYWORDS = new ApiInfo("SearchByKeywords", Method.POST, "/api/vikingdb/data/search/keywords");
    public static final ApiInfo SEARCH_BY_RANDOM = new ApiInfo("SearchByRandom", Method.POST, "/api/vikingdb/data/search/random");

    public static final ApiInfo FETCH_DATA_IN_INDEX = new ApiInfo("FetchDataInIndex", Method.POST, "/api/vikingdb/data/fetch_in_index");
    public static final ApiInfo AGGREGATE = new ApiInfo("Aggregate", Method.POST, "/api/vikingdb/data/agg");
    public static final ApiInfo SORT = new ApiInfo("Sort", Method.POST, "/api/vikingdb/data/sort");

    public static final ApiInfo Embedding = new ApiInfo("Embedding", Method.POST, "/api/vikingdb/embedding");
}
