package org.rpc.processor.impl;

import com.google.gson.Gson;
import org.rpc.http.client.XHttpClient;
import org.rpc.http.client.XHttpClient.XHttpClientCallback;
import org.rpc.http.client.XHttpClient.XHttpResponse;
import org.rpc.processor.RpcReply;

import java.util.Optional;

public class HttpRpcReply<T> implements RpcReply<T> {

    private final HttpCallStack rpcCallStack;

    private T value;
    private XHttpResponse response;
    private boolean executed = false;


    public HttpRpcReply(HttpCallStack rpcCallStack) {
        this.rpcCallStack = rpcCallStack;
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

        XHttpClientCallback callback = c -> {
            response = c;
            if (c.statusCode == XHttpClient.CODE_OK) {
                value = new Gson().fromJson(c.reply, rpcCallStack.returnType);
            }
        };

        rpcCallStack.execute(callback);

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
        String bodyText = rpcCallStack.body != null ? new Gson().toJson(rpcCallStack.body) : "NA";
        return "HttpRpcReply{" +
                " method='" + rpcCallStack.method + '\'' +
                ", header='" + rpcCallStack.headers + '\'' +
                ", url='" + rpcCallStack.url + '\'' +
                ", queryParams='" + rpcCallStack.queryParams + '\'' +
                ", body='" + bodyText + '\'' +
                ", returnType=" + rpcCallStack.returnType +
                '}';
    }
}
