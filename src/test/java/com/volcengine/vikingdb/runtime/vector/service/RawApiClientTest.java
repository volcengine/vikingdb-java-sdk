// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.vector.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.vikingdb.runtime.core.*;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.api.ApiInfo;
import com.volcengine.vikingdb.runtime.vector.model.request.FetchDataInCollectionRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.FetchDataInCollectionResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class RawApiClientTest {
    private static Properties properties;

    @BeforeAll
    public static void setUp() throws Exception {
        properties = new Properties();
        try (InputStream input = ApiClientTest.class.getClassLoader().getResourceAsStream("test-config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find test-config.properties");
            }
            properties.load(input);
        }
    }

    @Test
    public void testFetchDataInCollectionWithRawApiClient() throws Exception {
        ApiClient apiClient = new ApiClient(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                properties.getProperty("region"),
                new AuthWithAkSk(properties.getProperty("ak"), properties.getProperty("sk"))
        );

        FetchDataInCollectionRequest fetchDataInCollection = FetchDataInCollectionRequest.builder()
                .collectionName(TestVar.COLLECTION_NAME_VECTORIZE).ids(Arrays.asList("1", "2")).build();
        String body = ApiClient.objectMapper.writeValueAsString(fetchDataInCollection);
        System.out.println(body);
        ResponseContext rc = apiClient.doRequest(Method.POST, ApiInfo.FETCH_DATA_IN_COLLECTION.getPath(), body, new RequestAddition().setRequestId("test_req_id"));
        System.out.println(rc);
        System.out.println(rc.getBody());
        System.out.println(rc.getHeaders());
        System.out.println(rc.getHttpStatusCode());
        TypeReference<DataApiResponse<FetchDataInCollectionResult>> typeReference = new TypeReference<DataApiResponse<FetchDataInCollectionResult>>() {};
        DataApiResponse<FetchDataInCollectionResult> response = ApiClient.objectMapper.readValue(rc.getBody(), typeReference);
        System.out.println(response);
    }

}
