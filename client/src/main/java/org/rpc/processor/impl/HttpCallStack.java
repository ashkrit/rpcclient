package org.rpc.processor.impl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpCallStack {

    public String method;
    public String url;
    public Type returnType;

    public Map<String, String> headers = new HashMap<>();

    public Map<String, String> queryParams = new LinkedHashMap<>();

    public Object body;

    public String buildUrl() {
        String urlValue = url;
        if (hasValue()) {
            String params = queryParams
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));
            urlValue += "?";
            urlValue += params;
        }
        return urlValue;
    }

    private boolean hasValue() {
        return queryParams != null && !queryParams.isEmpty();
    }
}
