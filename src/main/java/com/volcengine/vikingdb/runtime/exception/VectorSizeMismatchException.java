package com.volcengine.vikingdb.runtime.exception;

public class VectorSizeMismatchException extends IllegalArgumentException {
    public VectorSizeMismatchException(int vectorSize1, int vectorSize2) {
        super(String.format("vectorSize are: %d and %d, which are mismatched", vectorSize1, vectorSize2));
    }

}
