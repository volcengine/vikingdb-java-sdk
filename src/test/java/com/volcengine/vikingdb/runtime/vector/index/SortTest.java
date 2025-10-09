// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.index;

import com.volcengine.vikingdb.runtime.Util;
import com.volcengine.vikingdb.runtime.core.ApiClientTest;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.model.request.SortRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.SortResult;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class SortTest {
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
    public void testSortV1() throws Exception {
        SortRequest request = SortRequest.builder()
                .collectionName(TestVar.COLLECTION_NAME)
                .indexName(TestVar.INDEX_NAME_SORT)
                .queryVector(Util.generateRandomVector(4))
                .ids(Arrays.asList(1, 2, 3, -7))
                .build();
        DataApiResponse<SortResult> response = service.sort(request);
        System.out.println(response);
    }
}
