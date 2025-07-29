package com.volcengine.vikingdb.runtime.vector.index;

import com.volcengine.vikingdb.runtime.core.ApiClientTest;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.model.request.AggregateRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.AggregateResult;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class AggregateTest {
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
    public void testSearchByScalarV1() throws Exception {
        AggregateRequest request = AggregateRequest.builder()
                .collectionName(TestVar.COLLECTION_NAME)
                .indexName(TestVar.INDEX_NAME)
                .filter(new HashMap<String, Object>(){{
                    put("op", "must_not");
                    put("field", "f_int64_2");
                    put("conds", Arrays.asList(3, 33));
                }})
                .op("count")
                .field("f_int64_2")
                .build();
        DataApiResponse<AggregateResult> response = service.aggregate(request);
        System.out.println(response);
    }
}
