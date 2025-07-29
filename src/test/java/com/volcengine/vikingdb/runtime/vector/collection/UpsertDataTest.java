package com.volcengine.vikingdb.runtime.vector.collection;

import com.volcengine.vikingdb.runtime.Util;
import com.volcengine.vikingdb.runtime.core.*;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.vector.TestUtil;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.model.request.UpsertDataRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.UpsertDataResult;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class UpsertDataTest {

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
    public void testUpsertDataV1() throws Exception {
        UpsertDataRequest request = UpsertDataRequest
                .builder()
                .collectionName(TestVar.COLLECTION_NAME)
                .data(Arrays.asList(
                        new HashMap<String,Object>(){{put("f_int64", 1);put("f_int64_2", 1);put("f_string", "doc1");put("f_vector", Util.generateRandomVector(4));}},
                        new HashMap<String,Object>(){{put("f_int64", 2);put("f_int64_2", 2);put("f_string", "doc2");put("f_vector", Util.generateRandomVector(4));}},
                        new HashMap<String,Object>(){{put("f_int64", 3);put("f_int64_2", 3);put("f_string", "doc3");put("f_vector", Util.generateRandomVector(4));}}
                ))
                .build();
        DataApiResponse<UpsertDataResult> response = service.upsertData(request);
        TestUtil.printResponse(response);
    }
}
