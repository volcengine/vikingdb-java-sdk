// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.core.retry;

import java.util.concurrent.Callable;

/**
 * A strategy that does not perform any retries.
 * It executes the action only once.
 */
public class NoRetryStrategy implements RetryStrategy {

    @Override
    public <T> T execute(Callable<T> action) throws Exception {
        return action.call();
    }
}
