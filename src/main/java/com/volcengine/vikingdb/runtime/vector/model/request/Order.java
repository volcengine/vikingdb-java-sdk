package com.volcengine.vikingdb.runtime.vector.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Order {
    @JsonProperty("asc")
    ASC,

    @JsonProperty("desc")
    DESC
}
