package org.rpc.service;

import org.rpc.service.model.*;
import org.rpc.processor.RpcBuilder;
import org.rpc.processor.RpcReply;
import org.rpc.service.model.GoogleEmbedding.GoogleEmbeddingReply;

import java.util.Arrays;
import java.util.Collections;

public class ServiceTest {

    public static void main(String[] args) {

        //_local();
        //_google();
        //_openAI();
        _groq();

    }

    private static void _groq() {
        RpcBuilder builder = new RpcBuilder().serviceUrl("https://api.groq.com/");
        GroqService service = builder.create(GroqService.class);

        Conversation conversation = new Conversation("llama2-70b-4096", 0.5f, false);
        conversation.append("user", "What is prompt Engineering");

        RpcReply<ConversationReply> reply = service.ask("Bearer " + System.getenv("gorq_key"), conversation);
        reply.execute();

        if (reply.isSuccess()) {
            System.out.println(reply.value().choices.get(0).message.content);
        } else {
            System.out.println(reply.error());
            System.out.println(reply.exception());
        }
    }

    private static void _openAI() {
        RpcBuilder builder = new RpcBuilder().serviceUrl("https://api.openai.com/");
        OpenAIService openAIService = builder.create(OpenAIService.class);

        OpenAIEmbedding openAIEmbedding = new OpenAIEmbedding("text-embedding-3-small", "How are you");
        String apiKey = "Bearer " + System.getenv("gpt_key");
        RpcReply<OpenAIEmbedding.OpenAIEmbeddingReply> reply = openAIService.embedding(apiKey, openAIEmbedding);

        reply.execute();

        if (reply.isSuccess()) {
            System.out.println(Arrays.toString(reply.value().data.get(0).embedding));
        } else {
            System.out.println(reply.exception());
            System.out.println(reply.error());
        }


    }

    private static void _google() {
        RpcBuilder builder = new RpcBuilder().serviceUrl("https://generativelanguage.googleapis.com");
        GoogleAIService googleAIService = builder.create(GoogleAIService.class);

        RpcReply<GoogleEmbeddingReply> reply = googleAIService
                .embedding(System.getenv("gemma_key"), createGEmbeddings("how are you", "models/embedding-001"));

        reply.execute();

        if (reply.isSuccess()) {
            System.out.println(Arrays.toString(reply.value().embedding.values));
        } else {
            System.out.println(reply.error());
            System.out.println(reply.exception());
        }
    }

    private static GoogleEmbedding createGEmbeddings(String text, String modelName) {
        GoogleEmbedding.ModelPart part = new GoogleEmbedding.ModelPart(text);
        GoogleEmbedding.ModelContent content = new GoogleEmbedding.ModelContent(Collections.singletonList(part));
        return new GoogleEmbedding(modelName, content);
    }

    private static void _local() {
        RpcBuilder builder = new RpcBuilder().serviceUrl("http://localhost:9090/");

        EmbeddingService s = builder.create(EmbeddingService.class);

        RpcReply<ModelInfo> list = s.list();
        System.out.println(list.value().models);

        Embedding e = new Embedding("google", "embedding-001", "How are you");
        System.out.println(Arrays.toString(s.embedding(e).value().embedding));
    }
}
