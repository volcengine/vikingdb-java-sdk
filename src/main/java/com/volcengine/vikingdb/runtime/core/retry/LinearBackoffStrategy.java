// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.core.retry;

import com.volcengine.vikingdb.runtime.core.Const;
import com.volcengine.vikingdb.runtime.exception.ExecuteHttpRequestException;
import com.volcengine.vikingdb.runtime.exception.RetryFailedException;
import com.volcengine.vikingdb.runtime.exception.TooManyRequestException;
import lombok.Getter;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A retry strategy with a fixed delay between attempts.
 */
@Getter
public class LinearBackoffStrategy implements RetryStrategy {

    private final int maxRetries;
    private final long delayMillis;
    private final Boolean withJitter;

    public LinearBackoffStrategy() {
        this(Const.DEFAULT_MAX_RETRY_TIMES, Const.DEFAULT_RETRY_INITIAL_DELAY_MS, true);
    }

    /**
     * Constructor for production use.
     * @param maxRetries Maximum number of retries.
     * @param delayMillis Fixed delay in milliseconds between retries.
     */
    public LinearBackoffStrategy(int maxRetries, long delayMillis, boolean withJitter) {
        this.maxRetries = Math.max(0, maxRetries);
        this.delayMillis = Math.max(1, delayMillis);
        this.withJitter = withJitter;
    }

    @Override
    public <T> T execute(Callable<T> action) throws Exception {
        Exception lastException;
        for (int attempt = 0; ; attempt++) {
            try {
                return action.call();
            } catch (TooManyRequestException | ExecuteHttpRequestException e) {
                lastException = e;
                if (attempt >= maxRetries) {
                    throw new RetryFailedException(attempt, lastException);
                }
                long jitterDelay = withJitter ? ThreadLocalRandom.current().nextLong(delayMillis) : 0;
                try {
                    Thread.sleep(Math.min(jitterDelay + delayMillis, delayMillis));
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", interruptedException);
                }
            }
        }
    }
}
