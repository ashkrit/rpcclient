package org.rpc.processor.impl;

import org.rpc.http.client.XHttpClient;
import org.rpc.http.client.XHttpClient.XHttpClientCallback;

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

    private final XHttpClient client;

    public HttpCallStack(XHttpClient client) {
        this.client = client;
    }

    public String buildUrl() {
        return _appendParams(url);
    }

    private String _appendParams(String urlValue) {
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

    public void execute(XHttpClientCallback callback) {

        String url = buildUrl();

        if (method.equalsIgnoreCase("GET")) {
            client.get(url, headers, callback);
        } else if (method.equalsIgnoreCase("POST")) {
            client.post(url, headers, body, callback);
        } else {
            throw new RuntimeException("Unsupported HTTP method: " + method);
        }
    }
}
