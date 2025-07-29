// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.core.auth;

import com.volcengine.auth.ISignerV4;
import com.volcengine.auth.impl.SignerV4Impl;
import com.volcengine.model.Credentials;
import com.volcengine.service.SignableRequest;
import com.volcengine.vikingdb.runtime.core.Const;
import com.volcengine.vikingdb.runtime.core.RequestExecutor;
import com.volcengine.vikingdb.runtime.enums.Method;
import com.volcengine.vikingdb.runtime.enums.Scheme;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

import java.util.List;
import java.util.Map;

public class Sign {

    public static SignableRequest prepareRequest(Scheme scheme, String host, String path, Method method,
                                                 Map<String, String> params, Map<String, String> headers,
                                                 String body, String ak, String sk, String region) throws Exception {
        SignableRequest request = new SignableRequest();
        request.setMethod(method.name());
        RequestExecutor.setHeaders(request, headers);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Host", host);
        request.setEntity(new StringEntity(body, "utf-8"));

        URIBuilder builder = request.getUriBuilder();
        builder.setScheme(scheme.toString());
        builder.setHost(host);
        builder.setPath(path);

        List<NameValuePair> nameValuePairs = new java.util.ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nameValuePairs.add(new org.apache.http.message.BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        if (params != null) {
            builder.setParameters(nameValuePairs);
        }

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(12000).build();
        request.setConfig(requestConfig);

        Credentials credentials = new Credentials(
                region,
                Const.DEFAULT_SERVICE);
        credentials.setAccessKeyID(ak);
        credentials.setSecretAccessKey(sk);

        ISignerV4 ISigner = new SignerV4Impl();
        ISigner.sign(request, credentials);

        return request;
    }
}
