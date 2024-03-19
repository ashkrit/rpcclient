package org.rpc.service;

import org.rpc.http.*;
import org.rpc.http.ClientApp.Embedding;
import org.rpc.http.ClientApp.Embedding.EmbeddingReply;
import org.rpc.processor.RpcReply;

public interface EmbeddingService {

    @XGET("list")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<ModelInfo> list();


    @XPOST("embedding")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<EmbeddingReply> embedding(@XBody Embedding embedding);

}
