// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.core;

import com.volcengine.vikingdb.runtime.core.retry.ExpBackoffStrategy;
import com.volcengine.vikingdb.runtime.core.retry.RetryStrategy;

import lombok.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode()
public class ClientConfig {
    @Builder.Default
    private HttpClient httpClient = HttpClients.createDefault();

    @Builder.Default
    private RetryStrategy retryStrategy = new ExpBackoffStrategy();
}
