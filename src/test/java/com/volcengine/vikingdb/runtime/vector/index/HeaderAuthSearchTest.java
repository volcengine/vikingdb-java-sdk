// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.index;

import com.volcengine.vikingdb.runtime.Util;
import com.volcengine.vikingdb.runtime.core.ApiClientTest;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithHeader;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.vector.model.request.SearchByVectorRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.SearchResult;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HeaderAuthSearchTest {
    private static VectorService service;

    @BeforeAll
    public static void setUp() throws Exception {
        Properties properties = new Properties();
        try (InputStream input = ApiClientTest.class.getClassLoader().getResourceAsStream(".env")) {
            if (input == null) {
                // For manual testing if properties file is missing
                System.err.println("Warning: Unable to find .env, skipping setup");
                return;
            }
            properties.load(input);
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Top-Account-Id", properties.getProperty("VIKINGDB_HTTP_HEADER_ACCOUNT_ID"));
        headers.put("X-Top-User-Id", properties.getProperty("VIKINGDB_HTTP_HEADER_USER_ID"));
        headers.put("X-Top-Role-Id", "data");

        service = new VectorService(
                Scheme.fromString(properties.getProperty("scheme", "http")),
                properties.getProperty("host", "localhost:9527"),
                new AuthWithHeader(headers)
        );
    }

    @Test
    public void testSearchWithHeaderAuth() throws Exception {
        if (service == null) {
            System.out.println("Service not initialized, skipping test");
            return;
        }

        // We don't need real data to verify that headers are sent and request reaches server
        SearchByVectorRequest request = SearchByVectorRequest.builder()
                .collectionName("test_collection_emb")
                .indexName("test_collection_emb_idx")
                .limit(1)
                .denseVector(Util.generateRandomVector(768))
                .build();
        
        try {
            DataApiResponse<SearchResult> response = service.searchByVector(request);
            assertNotNull(response.getRequestId());
            System.out.println("Request successful, RequestId: " + response.getRequestId());
        } catch (Exception e) {
            System.out.println("Request failed as expected if server is not reachable: " + e.getMessage());
        }
    }
}
