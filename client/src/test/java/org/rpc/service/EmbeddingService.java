package org.rpc.service;

import org.rpc.http.*;
import org.rpc.service.model.Embedding;
import org.rpc.service.model.Embedding.EmbeddingReply;
import org.rpc.processor.RpcReply;
import org.rpc.service.model.ModelInfo;

public interface EmbeddingService {

    @XGET("list")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<ModelInfo> list();


    @XPOST("embedding")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<EmbeddingReply> embedding(@XBody Embedding embedding);

}
