// Copyright (c) 2025 Bytedance Ltd. and/or its affiliates
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.vector.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.volcengine.vikingdb.runtime.core.ApiClient;
import com.volcengine.vikingdb.runtime.core.RequestAddition;
import com.volcengine.vikingdb.runtime.core.ResponseContext;
import com.volcengine.vikingdb.runtime.exception.*;
import com.volcengine.vikingdb.runtime.vector.model.response.DataApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiRequestBinding<Request, Response extends DataApiResponse<Result>, Result> {

    private static final ApiInfo PING_API_INFO = ApiInfo.PING;

    private final ApiInfo apiInfo;
    private final Request request;
    private final TypeReference<Response> responseRef;

    public Response callApi(ApiClient apiClient)
            throws ApiClientException, VectorApiException {
        return callApi(apiClient, null);
    }

    public Response callApi(ApiClient apiClient, RequestAddition requestAddition)
            throws ApiClientException, VectorApiException {
        String body;
        ResponseContext rc;
        Response response;
        try {
            body = ApiClient.objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new SerializeRequestException(e.getMessage(), e.getCause());
        }
        try {
            rc = apiClient.getClientConfig().getRetryStrategy().execute(()-> {
                ResponseContext result = apiClient.doRequest(
                    apiInfo.getMethod(),
                    apiInfo.getPath(),
                    body,
                    requestAddition
                );
                if (result != null && result.getHttpStatusCode() == 429) {
                    throw new TooManyRequestException(result);
                }
                return result;
            });
        } catch (Exception e) {
            throw new ApiClientException(e.getMessage(), e);
        }
        try {
            response = ApiClient.objectMapper.readValue(rc.getBody(), getResponseRef());
        } catch (JsonProcessingException e) {
            throw new DeserializeResponseException(e.getMessage(), e.getCause());
        }
        if (rc.getHttpStatusCode() < 200 || rc.getHttpStatusCode() >= 300) {
            throw new VectorApiException(
                    rc.getHttpStatusCode(),
                    apiInfo.getName(),
                    response.getCode(),
                    response.getMessage(),
                    response.getRequestId(),
                    rc
            );
        }
        return response;
    }

    public static ResponseContext ping(ApiClient apiClient, RequestAddition requestAddition) throws ApiClientException, VectorApiException {
        ResponseContext rc;
        try {
            rc = apiClient.doRequest(
                    PING_API_INFO.getMethod(),
                    PING_API_INFO.getPath(),
                    "",
                    requestAddition
            );
        } catch (Exception e) {
            throw new ApiClientException(e.getMessage(), e);
        }
        if (rc.getHttpStatusCode() < 200 || rc.getHttpStatusCode() >= 300) {
            throw new VectorApiException(rc.getHttpStatusCode(), ApiInfo.PING.getName(), "", "", "", rc);
        }
        return rc;
    }

}
