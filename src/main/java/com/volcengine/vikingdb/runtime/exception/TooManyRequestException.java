package com.volcengine.vikingdb.runtime.exception;

import com.volcengine.vikingdb.runtime.core.ResponseContext;

public class TooManyRequestException extends RuntimeException {

    private final ResponseContext responseContext;

    public TooManyRequestException(ResponseContext rc) {
        this.responseContext = rc;
    }

    @Override
    public String toString() {
        return "TooManyRequestException{" + "responseContext=" + responseContext + '}';
    }

    @Override
    public String getMessage() {
        return toString();
    }
}
