package org.rpc.service.main;


import org.rpc.http.*;
import org.rpc.service.RpcReply;
import org.rpc.service.main.model.OpenAIEmbedding;

import static org.rpc.service.main.model.OpenAIEmbedding.*;

public interface OpenAIService {

    @XPOST("/v1/embeddings")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<OpenAIEmbeddingReply> embedding(@XHeader("Authorization") String apiKey, @XBody OpenAIEmbedding embedding);
}
