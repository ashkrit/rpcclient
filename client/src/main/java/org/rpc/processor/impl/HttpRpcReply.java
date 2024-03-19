package org.rpc.processor.impl;

import com.google.gson.Gson;
import org.rpc.http.client.XHttpClient;
import org.rpc.http.client.XHttpClient.XHttpClientCallback;
import org.rpc.http.client.XHttpClient.XHttpResponse;
import org.rpc.processor.RpcReply;

import java.util.Optional;
import java.util.stream.Collectors;

public class HttpRpcReply<T> implements RpcReply<T> {

    private final HttpCallStack callInfo;
    private final XHttpClient httpClient;
    private T value;
    private XHttpResponse response;
    private boolean executed = false;


    public HttpRpcReply(HttpCallStack callInfo, XHttpClient httpClient) {
        this.callInfo = callInfo;
        this.httpClient = httpClient;
    }

    @Override
    public T value() {
        _executeIfRequired();
        return value;
    }

    private void _executeIfRequired() {
        if (!executed) {
            execute();
        }
    }

    @Override
    public void execute() {

        XHttpClientCallback clientCallback = c -> {
            response = c;
            if (c.statusCode == XHttpClient.CODE_OK) {
                value = new Gson().fromJson(c.reply, callInfo.returnType);
            }
        };
        String method = callInfo.method;
        String url = callInfo.buildUrl();

        if (method.equalsIgnoreCase("GET")) {
            httpClient.get(url, callInfo.headers, clientCallback);
        } else if (method.equalsIgnoreCase("POST")) {
            httpClient.post(url, callInfo.headers, callInfo.body, clientCallback);
        } else {
            throw new RuntimeException("Unsupported HTTP method: " + method);
        }

        executed = true;
    }


    @Override
    public int statusCode() {
        return response.statusCode;
    }

    @Override
    public boolean isSuccess() {
        return response.statusCode == XHttpClient.CODE_OK;
    }

    @Override
    public Optional<String> reply() {
        return response.reply();
    }

    @Override
    public Optional<String> error() {
        return response.error();
    }

    @Override
    public Optional<Exception> exception() {
        return response.exception();
    }


    @Override
    public String toString() {
        String bodyText = callInfo.body != null ? new Gson().toJson(callInfo.body) : "NA";
        return "HttpRpcReply{" +
                " method='" + callInfo.method + '\'' +
                ", header='" + callInfo.headers + '\'' +
                ", url='" + callInfo.url + '\'' +
                ", queryParams='" + callInfo.queryParams + '\'' +
                ", body='" + bodyText + '\'' +
                ", returnType=" + callInfo.returnType +
                '}';
    }
}
