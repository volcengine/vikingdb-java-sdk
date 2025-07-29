package com.volcengine.vikingdb.runtime.core;

import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClientConfigTest {
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
        for (int i = 0; i < 10; i++) {
            ResponseContext rc = apiClient.doRequest(Method.POST, "/api/vikingdb/Ping", null);
            System.out.println(rc);
        }
    }
}
