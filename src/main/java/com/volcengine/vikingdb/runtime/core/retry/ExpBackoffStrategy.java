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
 * A retry strategy that increases the delay exponentially between attempts with jitter.
 * This is the default strategy.
 */
@Getter
public class ExpBackoffStrategy implements RetryStrategy {

    private final int maxRetries;
    private final long initialDelayMillis;
    private final long maxDelayMillis;
    private final Boolean withJitter;

    private static final double MULTIPLIER = 2.0;

    public ExpBackoffStrategy() {
        this(Const.DEFAULT_MAX_RETRY_TIMES, Const.DEFAULT_RETRY_INITIAL_DELAY_MS, Const.DEFAULT_RETRY_MAX_DELAY_MS, true);
    }

    /**
     * Constructor for production use.
     *
     * @param maxRetries         Maximum number of retries.
     * @param initialDelayMillis Initial delay in milliseconds.
     * @param maxDelayMillis     Maximum delay in milliseconds.
     */
    public ExpBackoffStrategy(int maxRetries, long initialDelayMillis, long maxDelayMillis, boolean withJitter) {
        this.maxRetries = Math.max(0, maxRetries);
        this.initialDelayMillis = Math.max(1, initialDelayMillis);
        this.maxDelayMillis = Math.max(this.initialDelayMillis, maxDelayMillis);
        this.withJitter = withJitter;
    }

    @Override
    public <T> T execute(Callable<T> action) throws Exception {
        Exception lastException;
        long currentDelay = initialDelayMillis;
        for (int attempt = 0; ; attempt++) {
            try {
                return action.call();
            } catch (TooManyRequestException | ExecuteHttpRequestException e) {
                lastException = e;
                if (attempt >= maxRetries) {
                    throw new RetryFailedException(attempt, lastException);
                }
                long jitterDelay = withJitter ? ThreadLocalRandom.current().nextLong(currentDelay) : 0;
                try {
                    Thread.sleep(Math.min(jitterDelay + currentDelay, maxDelayMillis));
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", interruptedException);
                }
                currentDelay = (long) Math.min(currentDelay * MULTIPLIER, maxDelayMillis);
            }
        }
    }
}