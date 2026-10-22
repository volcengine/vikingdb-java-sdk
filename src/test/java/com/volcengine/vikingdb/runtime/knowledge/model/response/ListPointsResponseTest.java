package com.volcengine.vikingdb.runtime.knowledge.model.response;

import com.volcengine.vikingdb.runtime.core.ApiClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ListPointsResponseTest {
    @Test
    public void shouldDeserializeNestedServiceFieldsIntoData() throws Exception {
        String payload = "{"
                + "\"code\":0,"
                + "\"message\":\"ok\","
                + "\"request_id\":\"req-1\","
                + "\"data\":{"
                + "\"collection_name\":\"knowledge-a\","
                + "\"count\":1,"
                + "\"total_num\":2,"
                + "\"point_list\":[{\"point_id\":\"point-1\",\"content\":\"hello\"}]"
                + "}"
                + "}";

        ListPointsResponse response = ApiClient.objectMapper.readValue(payload, ListPointsResponse.class);

        assertNotNull(response.getData());
        assertEquals("knowledge-a", response.getData().getCollectionName());
        assertEquals(Integer.valueOf(1), response.getData().getCount());
        assertEquals(Integer.valueOf(2), response.getData().getTotalNum());
        assertNotNull(response.getData().getPointList());
        assertEquals(1, response.getData().getPointList().size());
        assertEquals("point-1", response.getData().getPointList().get(0).getPointId());
    }
}
