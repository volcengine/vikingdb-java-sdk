package com.volcengine.vikingdb.runtime.core;

import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.exception.BuildUriException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class RequestExecutorWithNoAuth implements RequestExecutor {

    private final ApiClient apiClient;

    public RequestExecutorWithNoAuth(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public ResponseContext execute(Method method, String path, Map<String, String> headers, Map<String, String> params, String body) {
        long startTime = System.currentTimeMillis();
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(apiClient.getScheme().toString())
                .setHost(apiClient.getHost())
                .setPath(path);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.addParameter(entry.getKey(), entry.getValue());
            }
        }
        URI uri;
        try {
            uri = uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new BuildUriException(e.getMessage(), e);
        }

        HttpRequestBase request = RequestExecutor.buildRequest(method, uri, body);
        RequestExecutor.setHeaders(request, headers);
        return RequestExecutor.getResponse(request, apiClient, startTime);

    }
}
