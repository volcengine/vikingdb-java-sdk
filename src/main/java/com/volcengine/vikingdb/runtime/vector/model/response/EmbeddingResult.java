package com.volcengine.vikingdb.runtime.vector.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmbeddingResult {
    @JsonProperty("data")
    private List<EmbeddingItem> data;

    @JsonProperty("token_usage")
    private Object tokenUsage;

}
