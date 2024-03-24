package org.rpc.service.impl;

import com.google.gson.internal.LinkedTreeMap;
import org.rpc.http.XGET;
import org.rpc.http.XPOST;
import org.rpc.http.client.XHttpClient;
import org.rpc.http.client.XHttpClient.XHttpClientCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HttpCallStack {

    public String method;
    public String url;
    public Type returnType;

    public Map<String, String> headers = new HashMap<>();

    public Map<String, String> queryParams = new LinkedHashMap<>();

    public Object body;

    private final XHttpClient client;

    private final Map<String, Consumer<XHttpClientCallback>> executor = new HashMap<>();
    public Map<String, String> pathParams = new HashMap<>();

    public HttpCallStack(XHttpClient client) {
        this.client = client;
        executor.put(XGET.class.getSimpleName(), this::_get);
        executor.put(XPOST.class.getSimpleName(), this::_post);
    }

    public String buildUrl() {
        return _appendParams(url);
    }

    private String _appendParams(String urlValue) {

        Function<String, String> pathFn = this::applyPathParams;
        Function<String, String> urlFn = this::applyUrlParam;

        return pathFn.andThen(urlFn).apply(urlValue);

    }

    private String applyUrlParam(String urlValue) {
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

    private String applyPathParams(String urlValue) {
        for (Map.Entry<String, String> paths : pathParams.entrySet()) {
            urlValue = urlValue.replace(String.format("{%s}", paths.getKey()), paths.getValue());
        }
        return urlValue;
    }

    private boolean hasValue() {
        return queryParams != null && !queryParams.isEmpty();
    }

    public void execute(XHttpClientCallback callback) {

        Optional<Consumer<XHttpClientCallback>> executorFn = Optional.ofNullable(executor.get(method));

        executorFn
                .ifPresent(executor -> executor.accept(callback));

        executorFn
                .orElseThrow(() -> new RuntimeException("Unsupported HTTP method: " + method));


    }

    private void _post(XHttpClientCallback callback) {
        client.post(buildUrl(), headers, body, callback);
    }

    private void _get(XHttpClientCallback callback) {
        client.get(buildUrl(), headers, callback);
    }
}
