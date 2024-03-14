package org.rpc.processor.impl;

import com.google.gson.Gson;
import org.rpc.processor.RpcReply;

public class HttpRpcReply<T> implements RpcReply<T> {

    private final HttpRPCCallInfo callInfo;


    public HttpRpcReply(HttpRPCCallInfo callInfo) {
        this.callInfo = callInfo;
    }

    @Override
    public T value() {
        return null;
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
