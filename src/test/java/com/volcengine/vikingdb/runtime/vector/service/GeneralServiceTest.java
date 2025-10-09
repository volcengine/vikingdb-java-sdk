// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.service;

import com.volcengine.vikingdb.runtime.core.ApiClientTest;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.core.ClientConfig;
import com.volcengine.vikingdb.runtime.core.retry.LinearBackoffStrategy;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.vector.TestUtil;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.model.request.FetchDataInCollectionRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.FetchDataInCollectionResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class GeneralServiceTest {
    private static VectorService vectorService;

    @BeforeAll
    public static void setUp() throws Exception {
        Properties properties = new Properties();
        try (InputStream input = ApiClientTest.class.getClassLoader().getResourceAsStream("test-config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find test-config.properties");
            }
            properties.load(input);
        }

        vectorService = new VectorService(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                properties.getProperty("region"),
                new AuthWithAkSk(properties.getProperty("ak"), properties.getProperty("sk")),
                ClientConfig.builder()
                        .retryStrategy(new LinearBackoffStrategy())
                        .build()
        );
    }

    @Test
    public void testFetchDataInCollectionV1() throws Exception {
        FetchDataInCollectionRequest request = FetchDataInCollectionRequest
                .builder()
                .collectionName(TestVar.COLLECTION_NAME_VECTORIZE)
                .ids(Arrays.asList("1", "2", "3", "-99"))
                .build();
        DataApiResponse<FetchDataInCollectionResult> response = vectorService.fetchDataInCollection(
                request
        );
        TestUtil.printFetchCollectionResult(response);
    }

}
