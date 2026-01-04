// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.rerank;

import com.volcengine.vikingdb.runtime.core.ApiClientTest;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.exception.ApiClientException;
import com.volcengine.vikingdb.runtime.exception.VectorApiException;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.model.request.EmbeddingDataItem;
import com.volcengine.vikingdb.runtime.vector.model.request.EmbeddingRequest;
import com.volcengine.vikingdb.runtime.vector.model.request.FullModalData;
import com.volcengine.vikingdb.runtime.vector.model.request.RerankRequest;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.EmbeddingResult;
import com.volcengine.vikingdb.runtime.vector.model.response.RerankResult;
import com.volcengine.vikingdb.runtime.vector.service.VectorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class RerankTest {
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
    public void testRerankForText() throws Exception {
        RerankRequest request = RerankRequest.builder()
                .modelName("doubao-seed-rerank")
                .modelVersion("251028")
                .query(Collections.singletonList(FullModalData.builder().text("apple").build()))
                .instruction("Whether the Document answers the Query or matches the content retrieval intent")
                .returnOriginData(true)
                .data(Arrays.asList(
                        Collections.singletonList(FullModalData.builder().text("This is an apple.").build()),
                        Collections.singletonList(FullModalData.builder().text("My name is John.").build()),
                        Collections.singletonList(FullModalData.builder().text("How many fruits are there?").build())
                ))
                .build();
        DataApiResponse<RerankResult> response = service.rerank(request);
        System.out.println(response);
    }

    @Test
    public void testEmbeddingForMultiModal() throws Exception {
        RerankRequest request = RerankRequest.builder()
                .modelName("doubao-seed-rerank")
                .modelVersion("251028")
                .query(Collections.singletonList(FullModalData.builder().text("iceberg").build()))
                .instruction("Whether the Document answers the Query or matches the content retrieval intent")
                .returnOriginData(true)
                .data(Arrays.asList(
                        Collections.singletonList(FullModalData.builder().text("Here is a mountain and it is very beautiful.").build()),
                        Collections.singletonList(FullModalData.builder().image("https://ark-project.tos-cn-beijing.volces.com/images/view.jpeg").build()),
                        Collections.singletonList(FullModalData.builder().video("https://ark-project.tos-cn-beijing.volces.com/doc_video/ark_vlm_video_input.mp4").build())
                )).build();
        DataApiResponse<RerankResult> response = service.rerank(request);
        System.out.println(response);
    }

}
