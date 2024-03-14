package org.rpc.processor.impl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRPCCallInfo {

    public String method;
    public String url;
    public Type returnType;

    public Map<String, String> headers = new HashMap<>();

    public Map<String, String> queryParams = new LinkedHashMap<>();

    public Object body;
}
