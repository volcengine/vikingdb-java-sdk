package com.volcengine.vikingdb.runtime.vector.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SortResult {
    @JsonProperty("sort")
    private List<SortItem> sort;

    @JsonProperty("ids_not_exist")
    private List<Object> idsNotExist;

}
