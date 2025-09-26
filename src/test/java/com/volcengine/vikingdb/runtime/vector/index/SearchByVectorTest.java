// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.index;

import com.volcengine.vikingdb.runtime.Util;
import com.volcengine.vikingdb.runtime.core.ApiClientTest;
import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.vector.TestUtil;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.model.request.SearchAdvance;
import com.volcengine.vikingdb.runtime.vector.model.request.SearchByVectorRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.SearchResult;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

public class SearchByVectorTest {
    private static VectorService service;

    @BeforeAll
    public static void setUp() throws Exception {
        Properties properties = new Properties();
        try (InputStream input = ApiClientTest.class.getClassLoader().getResourceAsStream("test-config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find test-config.properties");
            }
            properties.load(input);
        }

        service = new VectorService(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                new AuthWithAkSk(properties.getProperty("ak"), properties.getProperty("sk"))
        );
    }

    @Test
    public void testSearchByVectorV1() throws Exception {
        SearchByVectorRequest request = SearchByVectorRequest.builder()
                .limit(5)
                .filter(new HashMap<String, Object>(){{
                    put("op", "must_not");
                    put("field", "f_int64_2");
                    put("conds", Arrays.asList(3));
                }})
                .advance(SearchAdvance.builder()
                        .idsIn(Arrays.asList(1, 2, 3, 4, 5)).build())
                .denseVector(Util.generateRandomVector(4))
                .build();
        DataApiResponse<SearchResult> response = service.searchByVector(request);
        TestUtil.printSearchResult(response);
    }

    @Test
    public void testSearchByVectorV2(TestInfo testInfo) throws Exception {
        SearchByVectorRequest request = SearchByVectorRequest.builder()
                .collectionName(TestVar.COLLECTION_NAME)
                .indexName(TestVar.INDEX_NAME)
                .limit(5)
                .filter(new HashMap<String, Object>(){{
                    put("op", "must_not");
                    put("field", "f_int64_2");
                    put("conds", Collections.singletonList(3));
                }})
                .advance(SearchAdvance.builder()
                        .idsIn(Arrays.asList(1, 2, 3, 4, 5)).build())
                .denseVector(Util.generateRandomVector(4))
                .build();
        DataApiResponse<SearchResult> response = service.searchByVector(request, new RequestAddition().setRequestId(TestUtil.genUtRequestId(testInfo)));
        TestUtil.printSearchResult(response);
    }
}
