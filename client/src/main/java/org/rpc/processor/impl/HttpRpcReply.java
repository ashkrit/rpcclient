package org.rpc.processor.impl;

import org.rpc.processor.RpcReply;

import java.lang.reflect.Type;
import java.util.Map;

public class HttpRpcReply<T> implements RpcReply<T> {

    private final String method;
    private final String url;
    private final Class<T> returnType;
    private final Map<String, String> headers;

    public HttpRpcReply(String method, String url, Type type, Map<String, String> headers) {
        this.method = method;
        this.url = url;
        this.returnType = (Class<T>) type;
        this.headers = headers;
    }

    @Override
    public T value() {
        return null;
    }

    @Override
    public String toString() {
        return "HttpRpcReply{" +
                "method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", header='" + headers + '\'' +
                ", returnType=" + returnType +
                '}';
    }
}
