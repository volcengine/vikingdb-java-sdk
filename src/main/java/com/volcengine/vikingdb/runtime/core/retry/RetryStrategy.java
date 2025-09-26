// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.core.retry;

import com.github.rholder.retry.RetryException;

import java.util.concurrent.Callable;

/**
 * An interface for defining retry strategies for API calls.
 *
 */
@FunctionalInterface
public interface RetryStrategy {

    /**
     * Executes the given action, applying the specific retry logic of the strategy.
     *
     * @param action The action to be executed, which is expected to return a result of type T.
     *               This action will be retried upon failure according to the strategy's rules.
     * @return The result of the action if it succeeds.
     * @throws Exception if the action ultimately fails after all retry attempts.
     */
    <T> T execute(Callable<T> action) throws Exception;
}
