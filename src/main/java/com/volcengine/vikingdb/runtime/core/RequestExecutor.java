package com.volcengine.vikingdb.runtime.core;

import com.volcengine.vikingdb.runtime.Util;
import com.volcengine.vikingdb.runtime.core.auth.Auth;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithAkSk;
import com.volcengine.vikingdb.runtime.core.auth.AuthWithApiKey;
import com.volcengine.vikingdb.runtime.enums.AuthType;
import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.exception.ExecuteHttpRequestException;
import com.volcengine.vikingdb.runtime.exception.GetResponseEntityException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.Map;
import java.util.Arrays;

public interface RequestExecutor {

    static RequestExecutor create(ApiClient apiClient, Auth auth) {
        if (auth != null) {
            if (auth.getAuthType() == AuthType.AK_SK) {
                AuthWithAkSk authWithAkSk = (AuthWithAkSk) auth;
                return new RequestExecutorWithAkSk(apiClient, authWithAkSk.getAk(), authWithAkSk.getSk());
            }
            if (auth.getAuthType() == AuthType.API_KEY) {
                AuthWithApiKey authWithApiKey = (AuthWithApiKey) auth;
                return new RequestExecutorWithApiKey(apiClient, authWithApiKey.getApiKey());
            }
        }
        return new RequestExecutorWithNoAuth(apiClient);
    }

    static HttpRequestBase buildRequest(Method method, URI uri, String body) {
        switch (method) {
            case GET:
                return new HttpGet(uri);
            case POST:
                HttpPost httpPost = new HttpPost(uri);
                if (body != null) {
                    StringEntity entity = new StringEntity(body, "UTF-8");
                    entity.setContentType("application/json; charset=utf-8");
                    httpPost.setEntity(entity);
                }
                return httpPost;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method.name());
        }
    }

    static void setHeaders(HttpRequestBase request, Map<String, String> headers) {
        if (headers != null) {
            headers.forEach(request::setHeader);
        }
    }

    static void setHeaders(HttpRequestBase request, Header[] headers) {
        if (headers != null) {
            Arrays.stream(headers).forEach(header -> request.setHeader(header.getName(), header.getValue()));
        }
    }

    static ResponseContext getResponse(HttpRequestBase request, ApiClient apiClient, long startTime) {
        HttpResponse response;
        try {
            response = apiClient.getClientConfig().getHttpClient().execute(request);
        } catch (Exception e) {
            throw new ExecuteHttpRequestException(e.getMessage(), e);
        }

        HttpEntity entity = response.getEntity();
        String responseBody;
        try {
            responseBody = entity != null ? EntityUtils.toString(entity) : "";
        } catch (Exception e) {
            throw new GetResponseEntityException(e.getMessage(), e);
        }

        int statusCode = response.getStatusLine().getStatusCode();
        return new ResponseContext(
                statusCode,
                System.currentTimeMillis() - startTime,
                Util.getHeadersMap(response.getAllHeaders()),
                responseBody
        );
    }

    ResponseContext execute(Method method, String path, Map<String, String> headers, Map<String, String> params, String body);
}
