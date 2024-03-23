package org.rpc.service.main;


import org.rpc.http.XBody;
import org.rpc.http.XHeaders;
import org.rpc.http.XPOST;
import org.rpc.http.XQuery;
import org.rpc.service.RpcReply;
import org.rpc.service.main.model.GoogleEmbedding;
import org.rpc.service.main.model.GoogleEmbedding.GoogleEmbeddingReply;


public interface GoogleAIService {

    @XPOST("/v1beta/models/embedding-001:embedContent")
    @XHeaders({"Content-Type: application/json"})
    RpcReply<GoogleEmbeddingReply> embedding(@XQuery("key") String apiKey, @XBody GoogleEmbedding embedding);

}
