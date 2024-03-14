package org.rpc.service;

import org.rpc.http.XGET;
import org.rpc.http.XHeader;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.processor.RpcReply;

public interface SampleService {

    @XGET("list")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<Models> list();


    @XPOST("embedding")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<Models> embeddings(@XHeader("api") String key, @XHeader("token") String token);
}
