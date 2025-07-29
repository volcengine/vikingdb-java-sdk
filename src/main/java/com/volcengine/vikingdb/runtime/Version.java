// Copyright (c) 2025 Beijing Volcano Engine Technology Co., Ltd.
// SPDX-License-Identifier: Apache-2.0

package com.volcengine.vikingdb.runtime;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Version {
    private static final String VERSION_PROPERTIES = "version.properties";
    private static final String PROJECT_VERSION_KEY = "project.version";

    @Getter
    private static String version = "unknown";

    static {
        Properties properties = new Properties();
        try (InputStream inputStream = Version.class.getClassLoader().getResourceAsStream(VERSION_PROPERTIES)) {
            if (inputStream != null) {
                properties.load(inputStream);
                version = properties.getProperty(PROJECT_VERSION_KEY);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
