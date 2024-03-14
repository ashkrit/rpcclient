package org.rpc.processor;

import java.lang.reflect.Proxy;

public class RpcBuilder {

    private String serviceUrl;

    public <T> T create(final Class<T> serviceClass) {
        ServiceProxy driver = new ServiceProxy(this);
        Object o = Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[]{serviceClass}, driver);
        return (T) o;
    }

    public RpcBuilder serviceUrl(String url) {
        this.serviceUrl = url;
        return this;
    }

    public String serviceUrl() {
        return serviceUrl.endsWith("/") ? serviceUrl : serviceUrl + "/";
    }
}
