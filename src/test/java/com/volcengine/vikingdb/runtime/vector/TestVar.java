// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector;

import com.volcengine.vikingdb.runtime.vector.model.request.EmbeddingModel;

public class TestVar {
    public static final String COLLECTION_NAME = "test_coll_for_java_sdk_with_vector";
    public static final String COLLECTION_NAME_VECTORIZE = "test_coll_for_java_sdk_with_vectorize";
    public static final String INDEX_NAME = "idx_1";
    public static final String INDEX_NAME_SORT = "idx_rs_1";
    public static final EmbeddingModel EMBEDDING_DENSE = new EmbeddingModel("doubao-embedding-vision", "250615", 1024);
    public static final EmbeddingModel EMBEDDING_SPARSE = new EmbeddingModel("bge-m3", "default");
}
