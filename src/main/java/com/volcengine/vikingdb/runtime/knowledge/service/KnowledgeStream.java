// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0 

package com.volcengine.vikingdb.runtime.knowledge.service;

import com.volcengine.vikingdb.runtime.core.ApiClient;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class KnowledgeStream<T> implements Iterable<T>, AutoCloseable {
    private final BufferedReader reader;
    private final Closeable closeable;
    private final Class<T> itemClass;
    private boolean closed = false;

    public KnowledgeStream(BufferedReader reader, Closeable closeable, Class<T> itemClass) {
        this.reader = reader;
        this.closeable = closeable;
        this.itemClass = itemClass;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private T nextItem;
            private boolean prepared = false;

            @Override
            public boolean hasNext() {
                if (closed) {
                    return false;
                }
                if (prepared) {
                    return nextItem != null;
                }
                prepared = true;
                nextItem = readNext();
                return nextItem != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                prepared = false;
                return nextItem;
            }

            private T readNext() {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (line.isEmpty()) {
                            continue;
                        }
                        if (!line.startsWith("data:")) {
                            continue;
                        }
                        String data = line.substring("data:".length()).trim();
                        if (data.isEmpty() || "[DONE]".equals(data)) {
                            close();
                            return null;
                        }
                        return ApiClient.objectMapper.readValue(data, itemClass);
                    }
                    close();
                    return null;
                } catch (Exception e) {
                    try {
                        close();
                    } catch (Exception ignored) {
                    }
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Override
    public void close() throws IOException {
        if (closed) {
            return;
        }
        closed = true;
        try {
            reader.close();
        } finally {
            if (closeable != null) {
                closeable.close();
            }
        }
    }
}
