package com.volcengine.vikingdb.runtime.vector.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchByRandomRequest extends BaseSearchRequest {

}
