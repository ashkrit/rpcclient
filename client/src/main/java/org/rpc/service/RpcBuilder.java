package org.rpc.service;

import org.rpc.http.client.ApacheHTTPClient;
import org.rpc.http.client.XHttpClient;

import java.lang.reflect.Proxy;

public class RpcBuilder {

    private String serviceUrl;
    private XHttpClient client = new ApacheHTTPClient();

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

    public XHttpClient client() {
        return client;
    }

    public String serviceUrl() {
        return serviceUrl.endsWith("/") ? serviceUrl.substring(0, serviceUrl.length() - 1) : serviceUrl;
    }
}
