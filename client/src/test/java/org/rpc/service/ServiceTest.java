package org.rpc.service;

import org.rpc.http.ClientApp;
import org.rpc.http.ClientApp.Embedding;
import org.rpc.processor.RpcBuilder;
import org.rpc.processor.RpcReply;
import org.rpc.service.GoogleEmbedding.GoogleEmbeddingReply;

import java.util.Arrays;
import java.util.Collections;

public class ServiceTest {

    public static void main(String[] args) {

        // _local();


        RpcBuilder builder = new RpcBuilder().serviceUrl("https://generativelanguage.googleapis.com");

        GoogleAIService googleAIService = builder.create(GoogleAIService.class);

        RpcReply<GoogleEmbeddingReply> reply = googleAIService
                .embedding(System.getenv("gemma_key"), createGEmbeddings("how are you", "models/embedding-001"));

        System.out.println(Arrays.toString(reply.value().embedding.values));

    }

    private static GoogleEmbedding createGEmbeddings(String text, String modelName) {
        GoogleEmbedding.ModelPart part = new GoogleEmbedding.ModelPart(text);
        GoogleEmbedding.ModelContent content = new GoogleEmbedding.ModelContent(Collections.singletonList(part));
        GoogleEmbedding embedding = new GoogleEmbedding(modelName, content);
        return embedding;
    }

    private static void _local() {
        RpcBuilder builder = new RpcBuilder().serviceUrl("http://localhost:9090");

        EmbeddingService s = builder.create(EmbeddingService.class);

        RpcReply<ModelInfo> list = s.list();
        System.out.println(list.value().models);

        Embedding e = new Embedding("google", "embedding-001", "How are you");
        System.out.println(Arrays.toString(s.embedding(e).value().embedding));
    }
}
