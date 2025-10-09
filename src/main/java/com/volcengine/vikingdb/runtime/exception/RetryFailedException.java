// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.exception;

public class RetryFailedException extends RuntimeException {
    private final int retriedTimes;

    public RetryFailedException(int retriedTimes, Throwable cause) {
        super(cause.getMessage(), cause);
        this.retriedTimes = retriedTimes;
    }

    @Override
    public String toString() {
        return "RetryFailedException{" +
                "retriedTimes=" + retriedTimes +
                ", message='" + super.getMessage() + '\'' +
                "} cause by: " + getCause();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
