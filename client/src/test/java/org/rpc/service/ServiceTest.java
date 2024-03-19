package org.rpc.service;

import org.rpc.http.ClientApp;
import org.rpc.http.ClientApp.Embedding;
import org.rpc.http.ClientApp.OpenAIEmbedding;
import org.rpc.processor.RpcBuilder;
import org.rpc.processor.RpcReply;
import org.rpc.service.GoogleEmbedding.GoogleEmbeddingReply;

import java.util.Arrays;
import java.util.Collections;

public class ServiceTest {

    public static void main(String[] args) {

        // _local();
        //_google();
        _openAI();


    }

    private static void _openAI() {
        RpcBuilder builder = new RpcBuilder().serviceUrl("https://api.openai.com/");
        OpenAIService openAIService = builder.create(OpenAIService.class);

        OpenAIEmbedding openAIEmbedding = new OpenAIEmbedding("text-embedding-3-small", "How are you");
        String apiKey = "Bearer " + System.getenv("gpt_key");
        RpcReply<OpenAIEmbedding.OpenAIEmbeddingReply> reply = openAIService.embedding(apiKey, openAIEmbedding);

        System.out.println(Arrays.toString(reply.value().data.get(0).embedding));
    }

    private static void _google() {
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
