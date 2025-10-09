// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.embedding;

import com.volcengine.vikingdb.runtime.core.ApiClientTest;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import com.volcengine.vikingdb.runtime.vector.TestVar;
import com.volcengine.vikingdb.runtime.vector.model.request.EmbeddingDataItem;
import com.volcengine.vikingdb.runtime.vector.model.request.EmbeddingRequest;
import com.volcengine.vikingdb.runtime.vector.model.request.FullModalData;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import com.volcengine.vikingdb.runtime.vector.model.response.EmbeddingResult;
import com.volcengine.vikingdb.runtime.vector.service.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class EmbeddingTest {
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
    public void testEmbeddingForText() throws Exception {
        EmbeddingRequest request = EmbeddingRequest.builder()
                .denseModel(TestVar.EMBEDDING_DENSE)
                .data(Arrays.asList(
                        EmbeddingDataItem.builder().text("我喜欢听歌").build(),
                        EmbeddingDataItem.builder().text("我喜欢雨天").build()
                ))
                .build();
        DataApiResponse<EmbeddingResult> response = service.embedding(request);
        System.out.println(response);
    }

    @Test
    public void testEmbeddingForTextWithSparse() throws Exception {
        EmbeddingRequest request = EmbeddingRequest.builder()
                .denseModel(TestVar.EMBEDDING_DENSE)
                .sparseModel(TestVar.EMBEDDING_SPARSE)
                .data(Arrays.asList(
                        EmbeddingDataItem.builder().text("我喜欢听歌").build(),
                        EmbeddingDataItem.builder().text("我喜欢雨天").build()
                ))
                .build();
        DataApiResponse<EmbeddingResult> response = service.embedding(request);
        System.out.println(response);
    }

    @Test
    public void testEmbeddingForTextAndImage() throws Exception {
        EmbeddingRequest request = EmbeddingRequest.builder()
                .denseModel(TestVar.EMBEDDING_DENSE)
                .data(Arrays.asList(
                        EmbeddingDataItem.builder().text("我喜欢听歌").image("tos://your_bucket/1.jpg").build(),
                        EmbeddingDataItem.builder().text("我喜欢雨天").build()
                ))
                .build();
        DataApiResponse<EmbeddingResult> response = service.embedding(request);
        System.out.println(response);
    }

    @Test
    public void testEmbeddingForFullModalSeq() throws Exception {
        EmbeddingRequest request = EmbeddingRequest.builder()
                .denseModel(TestVar.EMBEDDING_DENSE)
                .sparseModel(TestVar.EMBEDDING_SPARSE)
                .data(Collections.singletonList(EmbeddingDataItem.builder().fullModalSeq(Arrays.asList(
                            FullModalData.builder().text("我喜欢听歌").build(),
                            FullModalData.builder().image("tos://your_bucket/.1.jpg").build(),
                            FullModalData.builder().video("tos://your_bucket/.1.mp4").build()))
                .build())).build();
        DataApiResponse<EmbeddingResult> response = service.embedding(request);
        System.out.println(response);
    }
}
