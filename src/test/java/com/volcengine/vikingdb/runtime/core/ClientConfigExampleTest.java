// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.core;

import com.volcengine.vikingdb.runtime.core.retry.ExpBackoffStrategy;
import com.volcengine.vikingdb.runtime.core.retry.RetryStrategy;
import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * 该用例主要作为“示例代码”：演示如何在初始化时配置 ClientConfig：
 * - 设置重试策略（RetryStrategy）
 * - 自定义 Apache HttpClient（最大连接数、超时、建连时间等）
 */
public class ClientConfigExampleTest {

    @Test
    public void testBuildClientConfigWithCustomRetryAndHttpClient() {
        // 1) 重试策略：这里演示指数退避（ExpBackoff），并指定重试参数
        RetryStrategy retryStrategy = new ExpBackoffStrategy(
                3,    // 最大重试次数：失败后最多再尝试 3 次
                1000, // 初始退避时间(ms)：第一次重试前先等待多久
                8000, // 最大退避时间(ms)：退避上限，避免等待时间无限增长
                true  // 是否开启抖动(jitter)：加入随机扰动，缓解同一时间集中重试
        );

        // 2) 设置 HttpClient 连接池
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                60, TimeUnit.SECONDS // 连接存活时间(TTL)：连接在池中最多保留多久，避免长期复用导致异常
        );
        cm.setMaxTotal(200);          // 最大总连接数：整个连接池允许的最大并发连接数

        // 3) 设置 超时配置：
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(500) // 从连接池获取连接的超时(ms)：池满时最多等待多久
                .setConnectTimeout(1000)          // 建连超时(ms)：TCP 三次握手等建连过程的最大等待时间
                .setSocketTimeout(60000)           // 读超时(ms)：建立连接后等待响应数据的最大时间
                .build();

        // 4) 构建 HttpClient：
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)                    // 使用连接池配置
                .setDefaultRequestConfig(requestConfig)      // 使用超时配置
                .build();

        // 5) 把 重试策略 和 HttpClient 注入到 ClientConfig
        ClientConfig clientConfig = ClientConfig.builder()
                .retryStrategy(retryStrategy) // SDK 侧重试策略（用于 429/网络异常等场景）
                .httpClient(httpClient)       // SDK 侧 HttpClient（连接池/超时等都由你控制）
                .build();

        // 6) 把 apiClient 注入到 ClientConfig
        ApiClient apiClient = new ApiClient(
                Scheme.HTTPS,
                "api-vikingdb.vikingdb.cn-beijing.volces.com",
                "cn-beijing",
                null,
                clientConfig
        );

        ResponseContext rc = apiClient.doRequest(
                Method.POST,
                "/api/vikingdb/Ping",
                ""
        );
        System.out.println(rc.getHttpStatusCode()); // HTTP 状态码：200 表示成功
        System.out.println(rc.getHeaders());        // 响应头：可用于排查 request-id 等
    }

    @Test
    public void testClientConfigBuilderDefaults() {
        ClientConfig clientConfig = ClientConfig.builder().build();

        ApiClient apiClient = new ApiClient(
                Scheme.HTTPS,
                "api-vikingdb.vikingdb.cn-beijing.volces.com",
                "cn-beijing",
                null,
                clientConfig
        );
        ResponseContext rc = apiClient.doRequest(
                Method.POST,
                "/api/vikingdb/Ping",
                ""
        );
        System.out.println(rc.getHttpStatusCode()); // HTTP 状态码：200 表示成功
        System.out.println(rc.getHeaders());        // 响应头：可用于排查 request-id 等
    }
}
