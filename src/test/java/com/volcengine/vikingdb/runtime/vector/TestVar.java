package com.volcengine.vikingdb.runtime.vector;

import com.volcengine.vikingdb.runtime.vector.model.request.EmbeddingModel;

public class TestVar {
    public static final String COLLECTION_NAME = "test_liuyang_coll_2";
    public static final String COLLECTION_NAME_VECTORIZE = "test_liuyang_coll_vtz_2";
    public static final String INDEX_NAME = "idx_1";
    public static final String INDEX_NAME_SORT = "idx_rs_1";
    public static final EmbeddingModel EMBEDDING_DENSE = new EmbeddingModel("doubao-embedding-large", "240915", 1024);
    public static final EmbeddingModel EMBEDDING_SPARSE = new EmbeddingModel("bge-m3", "default");
}
