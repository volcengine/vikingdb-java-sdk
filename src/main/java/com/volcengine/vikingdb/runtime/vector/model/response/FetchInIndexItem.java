package com.volcengine.vikingdb.runtime.vector.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FetchInIndexItem {
    @JsonProperty("id")
    private Object id;

    @JsonProperty("fields")
    private Map<String, Object> fields;

    @JsonProperty("dense_vector")
    private List<Float> denseVector;

    @JsonProperty("dense_dim")
    private Integer denseDim;
}
