package com.volcengine.vikingdb.runtime.vector.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataApiResponse<T> {
    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @Nullable
    @JsonProperty("result")
    private T result;
}
