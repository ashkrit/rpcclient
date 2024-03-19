package org.rpc.service;


import org.rpc.http.*;
import org.rpc.service.model.OpenAIEmbedding;

import static org.rpc.service.model.OpenAIEmbedding.*;

public interface OpenAIService {

    @XPOST("/v1/embeddings")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<OpenAIEmbeddingReply> embedding(@XHeader("Authorization") String apiKey, @XBody OpenAIEmbedding embedding);
}
