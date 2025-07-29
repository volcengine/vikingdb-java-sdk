// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime.enums;

public enum Scheme {

    HTTP("http"),
    HTTPS("https");

    public static final Scheme DEFAULT = HTTPS;

    private final String lowerName;

    Scheme(String lowerName) {
        this.lowerName = lowerName;
    }

    @Override
    public String toString() {
        return lowerName;
    }

    public static Scheme fromString(String input) {
        if (input == null) {
            return DEFAULT;
        }
        switch (input.toLowerCase()) {
            case "http":
                return HTTP;
            case "https":
                return HTTPS;
            default:
                return DEFAULT;
        }
    }
}
