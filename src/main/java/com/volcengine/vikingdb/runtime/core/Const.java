package com.volcengine.vikingdb.runtime.core;

public class Const {
    public static final String DEFAULT_REGION = "cn-beijing";
    public static final String DEFAULT_SERVICE = "vikingdb";

    public static final String HOST_PREFIX_HTTP = "http://";
    public static final String HOST_PREFIX_HTTPS = "https://";

    public static final String HEADER_REQUEST_ID = "X-Tt-Logid";
    public static final String HEADER_USER_AGENT = "User-Agent";

    public static final String USER_AGENT_FORMAT = "vikingdb-java-sdk/%s";

    public static final Integer DEFAULT_RETRY_INITIAL_DELAY_MS = 500;
    public static final Integer DEFAULT_RETRY_MAX_DELAY_MS = 8000;
    public static final Integer DEFAULT_MAX_RETRY_TIMES = 3;
}
