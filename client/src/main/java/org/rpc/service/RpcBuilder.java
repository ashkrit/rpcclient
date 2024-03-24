package org.rpc.service;

import org.rpc.http.client.ApacheHTTPClient;
import org.rpc.http.client.XHttpClient;

import java.lang.reflect.Proxy;
import java.util.Optional;
import java.util.function.Function;

public class RpcBuilder {

    private String serviceUrl;
    private XHttpClient client = new ApacheHTTPClient();
    private Function<String, String> variableResolver = s -> Optional.ofNullable(System.getenv(s)).orElse(System.getProperty(s));

    public <T> T create(final Class<T> serviceClass) {
        ServiceProxy driver = new ServiceProxy(this);
        Object o = Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[]{serviceClass}, driver);
        return (T) o;
    }

    public RpcBuilder serviceUrl(String url) {
        this.serviceUrl = url;
        return this;
    }


    public RpcBuilder client(XHttpClient client) {
        this.client = client;
        return this;
    }

    public RpcBuilder setVariableResolver(Function<String, String> variableResolver) {
        this.variableResolver = variableResolver;
        return this;
    }

    public Function<String, String> variableResolver() {
        return variableResolver;
    }

    public XHttpClient client() {
        return client;
    }

    public String serviceUrl() {
        return serviceUrl.endsWith("/") ? serviceUrl.substring(0, serviceUrl.length() - 1) : serviceUrl;
    }
}
