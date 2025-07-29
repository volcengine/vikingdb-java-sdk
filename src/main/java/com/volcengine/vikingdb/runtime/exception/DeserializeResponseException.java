package com.volcengine.vikingdb.runtime.exception;

public class DeserializeResponseException extends RuntimeException {
    public DeserializeResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
