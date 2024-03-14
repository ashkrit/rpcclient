package org.rpc.processor;

import org.rpc.http.XGET;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.processor.impl.HttpRPCCallInfo;
import org.rpc.processor.impl.HttpRpcReply;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceProxy implements InvocationHandler {
    private final RpcBuilder builder;

    public ServiceProxy(RpcBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }

        Annotation[] tags = method.getDeclaredAnnotations();

        HttpRPCCallInfo callInfo = new HttpRPCCallInfo();
        ParameterizedType returnType = (ParameterizedType) method.getGenericReturnType();
        Type[] types = returnType.getActualTypeArguments();
        callInfo.returnType = types[0];

        for (Annotation tag : tags) {

            if (tag instanceof XGET) {
                XGET methodType = (XGET) tag;

                String url = String.format("%s%s", builder.serviceUrl(), methodType.value());
                callInfo.method = "GET";
                callInfo.url = url;


            } else if (tag instanceof XPOST) {
                XPOST methodType = (XPOST) tag;
                String url = String.format("%s%s", builder.serviceUrl(), methodType.value());
                callInfo.method = "POST";
                callInfo.url = url;
            } else if (tag instanceof XHeaders) {
                XHeaders headersParam = (XHeaders) tag;
                callInfo.headers.putAll(_headers(headersParam));
            }
        }
        return new HttpRpcReply<>(callInfo.method, callInfo.url, callInfo.returnType, callInfo.headers);

    }

    private static Map<String, String> _headers(XHeaders headers) {
        return Stream.of(headers.value())
                .map(x -> x.split(":"))
                .collect(Collectors.toMap(x -> x[0], x -> x[1].trim()));
    }

}
