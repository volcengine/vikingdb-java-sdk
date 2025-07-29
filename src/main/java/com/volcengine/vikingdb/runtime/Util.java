// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime;

import com.volcengine.vikingdb.runtime.core.Const;
import com.volcengine.vikingdb.runtime.exception.ParamEmptyException;
import com.volcengine.vikingdb.runtime.exception.VectorSizeMismatchException;
import org.apache.http.Header;

import java.util.*;

public class Util {
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static void requiresNonEmpty(String paramName, String paramValue) {
        if (isEmpty(paramValue)) {
            throw new ParamEmptyException(paramName);
        }
    }

    public static String trimHostPrefix(String host) {
        if (host.startsWith(Const.HOST_PREFIX_HTTP)) {
            host = host.substring(Const.HOST_PREFIX_HTTP.length());
        } else if (host.startsWith(Const.HOST_PREFIX_HTTPS)) {
            host = host.substring(Const.HOST_PREFIX_HTTPS.length());
        }
        return host;
    }

    public static Map<String, String> getHeadersMap(Header[] headers) {
        Map<String, String> headersMap = new HashMap<>();
        for (Header header : headers) {
            headersMap.put(header.getName(), header.getValue());
        }
        return headersMap;
    }

    public static List<Float> generateRandomVector(int n) {
        List<Float> randomList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            float randomFloat = (random.nextFloat() * 2) - 1;
            randomList.add(randomFloat);
        }
        return randomList;
    }

    public static float getCosineDistance(List<Float> vec1, List<Float> vec2) {
        if (vec1.size() != vec2.size()) {
            throw new VectorSizeMismatchException(vec1.size(), vec2.size());
        }
        float dotProduct = 0;
        float norm1 = 0;
        float norm2 = 0;
        for (int i = 0; i < vec1.size(); i++) {
            float a = vec1.get(i);
            float b = vec2.get(i);
            dotProduct += a * b;
            norm1 += a * a;
            norm2 += b * b;
        }
        return dotProduct / ( (float) Math.sqrt(norm1) * (float) Math.sqrt(norm2) );
    }

    public static float getInnerProductDistance(List<Float> vec1, List<Float> vec2) {
        if (vec1.size() != vec2.size()) {
            throw new VectorSizeMismatchException(vec1.size(), vec2.size());
        }
        float dotProduct = 0;
        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
        }
        return dotProduct;
    }

    public static float getL2Distance(List<Float> vec1, List<Float> vec2) {
        if (vec1.size() != vec2.size()) {
            throw new VectorSizeMismatchException(vec1.size(), vec2.size());
        }
        float sum = 0;
        for (int i = 0; i < vec1.size(); i++) {
            float diff = vec1.get(i) - vec2.get(i);
            sum += diff * diff;
        }
        return (float) Math.sqrt(sum);
    }
}
