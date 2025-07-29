package com.volcengine.vikingdb.runtime.vector.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FetchDataInIndexResult {
    @JsonProperty("fetch")
    private List<FetchInIndexItem> fetch;

    @JsonProperty("ids_not_exist")
    private List<Object> idsNotExist;
}
