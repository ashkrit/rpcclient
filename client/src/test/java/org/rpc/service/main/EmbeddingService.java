package org.rpc.service.main;

import org.rpc.http.*;
import org.rpc.service.RpcReply;
import org.rpc.service.main.model.Embedding;
import org.rpc.service.main.model.Embedding.EmbeddingReply;
import org.rpc.service.main.model.ModelInfo;

public interface EmbeddingService {

    @XGET("/list")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<ModelInfo> list();


    @XPOST("/embedding")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<EmbeddingReply> embedding(@XBody Embedding embedding);

}
