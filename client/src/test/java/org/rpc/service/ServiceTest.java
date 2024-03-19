package org.rpc.service;

import org.rpc.http.ClientApp.Embedding;
import org.rpc.processor.RpcBuilder;
import org.rpc.processor.RpcReply;

import java.util.Arrays;

public class ServiceTest {

    public static void main(String[] args) {

        RpcBuilder builder = new RpcBuilder().serviceUrl("http://localhost:9090");

        EmbeddingService s = builder.create(EmbeddingService.class);

        System.out.println(s);

        RpcReply<ModelInfo> list = s.list();
        System.out.println(list.value().models);

        Embedding e = new Embedding("google", "embedding-001", "How are you");
        System.out.println(Arrays.toString(s.embedding(e).value().embedding));

    }
}
