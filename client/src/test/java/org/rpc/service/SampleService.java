package org.rpc.service;

import org.rpc.http.*;
import org.rpc.processor.RpcReply;

public interface SampleService {

    @XGET("list")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<Models> list();


    @XPOST("embedding")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<Models> embeddings(@XHeader("api") String key, @XHeader("token") String token, @XBody Embedding embedding);


    @XGET("search")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<Models> search(@XQuery("q") String searchText);
}
