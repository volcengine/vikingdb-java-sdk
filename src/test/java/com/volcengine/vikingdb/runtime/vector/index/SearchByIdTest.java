package com.volcengine.vikingdb.runtime.vector.index;

import com.volcengine.vikingdb.runtime.core.ApiClientTest;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.vector.TestUtil;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.model.request.SearchAdvance;
import com.volcengine.vikingdb.runtime.vector.model.request.SearchByIdRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.SearchResult;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

public class SearchByIdTest {
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
    public void testSearchByIdV1() throws Exception {
        SearchByIdRequest request = SearchByIdRequest.builder()
                .collectionName(TestVar.COLLECTION_NAME)
                .indexName(TestVar.INDEX_NAME)
                .limit(5)
                .filter(new HashMap<String, Object>(){{
                    put("op", "must_not");
                    put("field", "f_int64_2");
                    put("conds", Arrays.asList(3, 9, 222));
                }})
                .advance(SearchAdvance.builder()
                        .idsIn(Arrays.asList(1, 2, 3, 4, 5)).build())
                .id(1)
                .build();
        DataApiResponse<SearchResult> response = service.searchById(request);
        System.out.println(response);
        TestUtil.printSearchResult(response);
    }

}
