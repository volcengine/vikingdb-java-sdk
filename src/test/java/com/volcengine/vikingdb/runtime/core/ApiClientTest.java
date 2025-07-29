// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.core;

import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithApiKey;
import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiClientTest {

    private static Properties properties;

    @BeforeAll
    public static void setUp() throws IOException {
        properties = new Properties();
        try (InputStream input = ApiClientTest.class.getClassLoader().getResourceAsStream("test-config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find test-config.properties");
            }
            properties.load(input);
        }
    }

    @Test
    public void testRequestExecutorWithNoAuth() throws Exception {
        ApiClient apiClient = new ApiClient(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                properties.getProperty("region"),
                null
        );
        ResponseContext rc = apiClient.doRequest(Method.POST, "/api/vikingdb/Ping", null);
        System.out.println(rc);
    }

    @Test
    public void testRequestExecutorWithApiKeyPing() throws Exception {
        ApiClient apiClient = new ApiClient(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                properties.getProperty("region"),
                new AuthWithApiKey( properties.getProperty("api_key"))
        );
        ResponseContext rc = apiClient.doRequest(Method.POST, "/api/vikingdb/Ping", "");
        System.out.println(rc);
    }

    @Test
    public void testRequestExecutorWithApiKeyFetch() throws Exception {
        ApiClient apiClient = new ApiClient(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                properties.getProperty("region"),
                new AuthWithApiKey( properties.getProperty("api_key"))
        );
        ResponseContext rc = apiClient.doRequest(Method.POST, "/api/vikingdb/data/fetch_in_collection",
                "{\"collection_name\":\"xxx\", \"ids\":[1,2]}");
        System.out.println(rc);
    }

    @Test
    public void testRequestExecutorWithAkSkPing() throws Exception {
        ApiClient apiClient = new ApiClient(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                properties.getProperty("region"),
                new AuthWithAkSk( properties.getProperty("ak"), properties.getProperty("sk"))
        );
        ResponseContext rc = apiClient.doRequest(Method.POST, "/api/vikingdb/Ping",  "");
        System.out.println(rc);
    }

    @Test
    public void testRequestExecutorWithAkSkFetch() throws Exception {
        ApiClient apiClient = new ApiClient(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                properties.getProperty("region"),
                new AuthWithAkSk( properties.getProperty("ak"), properties.getProperty("sk"))
        );
        ResponseContext rc = apiClient.doRequest(Method.POST, "/api/vikingdb/data/fetch_in_collection",
                "{\"collection_name\":\"xxx\", \"ids\":[1,2]}");
        System.out.println(rc);
    }

}
