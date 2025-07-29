package com.volcengine.vikingdb.runtime.vector.collection;

import com.volcengine.vikingdb.runtime.core.*;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.exception.ApiClientException;
import com.volcengine.vikingdb.runtime.exception.VectorApiException;
import com.volcengine.vikingdb.runtime.vector.TestUtil;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.model.request.FetchDataInCollectionRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.FetchDataInCollectionResult;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FetchDataInCollectionTest {
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
    public void testFetchDataInCollectionV1() throws Exception {
        FetchDataInCollectionRequest request = FetchDataInCollectionRequest
                .builder()
                .ids(Arrays.asList("1", "2", "3", "-99"))
                .build();
        DataApiResponse<FetchDataInCollectionResult> response = service.fetchDataInCollection(request);
        TestUtil.printFetchCollectionResult(response);
    }

    @Test
    public void testFetchDataInCollectionV2() throws Exception {
        FetchDataInCollectionRequest request = FetchDataInCollectionRequest
                .builder()
                .collectionName(TestVar.COLLECTION_NAME)
                .ids(Arrays.asList("1", "2", "3", "-99"))
                .build();
        AtomicInteger counter = new AtomicInteger();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 100, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000));
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(()->{
                DataApiResponse<FetchDataInCollectionResult> response = null;
                try {
                    response = service.fetchDataInCollection(request);
                } catch (ApiClientException | VectorApiException e) {
                    throw new RuntimeException(e);
                }
                if (!"Success".equals(response.getCode())) {
                    TestUtil.printResponse(response);
                }
                counter.incrementAndGet();
                if (counter.get() % 10 == 0) {
                    System.out.println("counter: " + counter.get());
                }
            });
        }
        TimeUnit.SECONDS.sleep(10);
        threadPoolExecutor.shutdown();
    }

}
