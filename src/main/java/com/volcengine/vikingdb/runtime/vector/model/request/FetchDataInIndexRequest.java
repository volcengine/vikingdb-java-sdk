package com.volcengine.vikingdb.runtime.vector.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class FetchDataInIndexRequest extends BaseIndexRequest {
    @JsonProperty("ids")
    private List<Object> ids;

    @JsonProperty("output_fields")
    private List<String> outputFields;

}
