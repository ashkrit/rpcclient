package org.rpc.service;


import org.rpc.http.*;
import org.rpc.processor.RpcReply;

import static org.rpc.http.ClientApp.OpenAIEmbedding.*;

public interface OpenAIService {

    @XPOST("/v1/embeddings")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<OpenAIEmbeddingReply> embedding(@XHeader("Authorization") String apiKey, @XBody ClientApp.OpenAIEmbedding embedding);
}
