package com.volcengine.vikingdb.runtime.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ResponseContext {
    private final int httpStatusCode;
    private final Long timeCostMilliSeconds;
    private final Map<String, String> headers;
    private final String body;

    @Override
    public String toString() {
        return "ResponseContext{" +
                "httpStatusCode=" + httpStatusCode +
                ", timeCostMilliSeconds=" + timeCostMilliSeconds +
                ", headers=" + headers +
                ", body='" + body +
                '\'' + '}';
    }
}