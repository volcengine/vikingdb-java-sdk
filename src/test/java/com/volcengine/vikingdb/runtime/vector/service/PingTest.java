package com.volcengine.vikingdb.runtime.vector.service;

import com.volcengine.vikingdb.runtime.core.ApiClientTest;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithApiKey;
import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PingTest {
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
    public void testPingV1() throws Exception {
        VectorService vectorService = new VectorService(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                null
        );
        System.out.println(vectorService.ping());
    }

    @Test
    public void testPingV2() throws Exception {
        VectorService service = new VectorService(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                new AuthWithApiKey(properties.getProperty("api_key"))
        );
        System.out.println(service.ping(new RequestAddition().setRequestId("test_ping_with_java_sdk")));
    }

    @Test
    public void testPingV3() throws Exception {
        VectorService service = new VectorService(
                Scheme.fromString(properties.getProperty("scheme")),
                properties.getProperty("host"),
                new AuthWithAkSk(properties.getProperty("ak"), properties.getProperty("sk"))
        );
        System.out.println(service.ping(new RequestAddition().setRequestId("test_ping_with_java_sdk")));
    }
}
