package org.rpc.processor;

import org.rpc.http.*;
import org.rpc.http.client.ApacheHTTPClient;
import org.rpc.http.client.XHttpClient;
import org.rpc.processor.impl.HttpCallStack;
import org.rpc.processor.impl.HttpRpcReply;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServiceProxy implements InvocationHandler {
    private final RpcBuilder builder;
    private final XHttpClient client = new ApacheHTTPClient();

    public ServiceProxy(RpcBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }

        HttpCallStack callInfo = new HttpCallStack();

        callInfo.returnType = returnTypes(method);

        _processMethodTags(method, callInfo);
        _processMethodParams(method, args, callInfo);

        return new HttpRpcReply<>(callInfo,new ApacheHTTPClient());

    }

    private static void _processMethodParams(Method method, Object[] args, HttpCallStack callInfo) {
        Annotation[][] tags = method.getParameterAnnotations();
        List<Annotation> methodParams = Stream.of(tags).flatMap(Stream::of).collect(Collectors.toList());

        for (int index = 0; index < methodParams.size(); index++) {
            Annotation param = methodParams.get(index);
            if (param instanceof XHeader) {
                XHeader headerParam = (XHeader) param;
                callInfo.headers.put(headerParam.value(), args[index].toString());
            } else if (param instanceof XQuery) {
                XQuery queryParam = (XQuery) param;
                callInfo.queryParams.put(queryParam.value(), args[index].toString());
            } else if (param instanceof XBody) {
                callInfo.body = args[index];
            }
        }
    }

    private void _processMethodTags(Method method, HttpCallStack callInfo) {
        Annotation[] tags = method.getDeclaredAnnotations();
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
    }

    private static Type returnTypes(Method method) {
        ParameterizedType returnType = (ParameterizedType) method.getGenericReturnType();
        return returnType.getActualTypeArguments()[0];
    }

    private static Map<String, String> _headers(XHeaders headers) {
        return Stream.of(headers.value())
                .map(_parseHeader())
                .collect(Collectors.toMap(x -> x[0], x -> x[1].trim()));
    }

    private static Function<String, String[]> _parseHeader() {
        return x -> x.split(":");
    }

}
