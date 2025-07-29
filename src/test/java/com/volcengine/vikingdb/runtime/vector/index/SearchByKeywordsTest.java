// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.vector.index;

import com.volcengine.vikingdb.runtime.core.ApiClientTest;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.vector.TestUtil;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.model.request.SearchByKeywordsRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.SearchResult;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class SearchByKeywordsTest {
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
    public void testSearchByKeywordsV1() throws Exception {
        SearchByKeywordsRequest request = SearchByKeywordsRequest.builder()
                .collectionName(TestVar.COLLECTION_NAME_VECTORIZE)
                .indexName(TestVar.INDEX_NAME)
                .filter(new HashMap<String, Object>(){{
                    put("op", "must_not");
                    put("field", "f_int64_1");
                    put("conds", Arrays.asList(3, 888, 999));
                }})
                .limit(10)
                .keywords(Arrays.asList("青羊", "成都", "四川", "中国", "中国人"))
                .caseSensitive(true)
                .build();
        DataApiResponse<SearchResult> response = service.searchByKeywords(request);
        System.out.println(response);
        TestUtil.printSearchResult(response);
    }

}
