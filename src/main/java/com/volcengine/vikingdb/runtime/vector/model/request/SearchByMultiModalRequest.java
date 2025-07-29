package com.volcengine.vikingdb.runtime.vector.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchByMultiModalRequest extends BaseSearchRequest {
    @JsonProperty("text")
    private String text;

    @JsonProperty("image")
    private String image;

    @JsonProperty("need_instruction")
    private Boolean needInstruction;
}
